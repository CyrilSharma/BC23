import asyncio
import os
import random
import sys
from asyncio import Semaphore

a_wins, b_wins = 0, 0

# Just run the command manually whenever the maps change.
# os.system("./gradlew listMaps > test/maps.txt")
# Make sure to clean up the output.
def read_maps():
    maps = []
    with open("test/maps.txt", "r") as f:
        f_iter = iter(f)
        for line in f_iter:
            data = line.rstrip()
            maps.append(data)
    return maps

def parse_results(path: str):
    a_won, b_won = 0, 0
    match_started = False
    with open(path, "r") as f:
        i = iter(f)
        for line in i:
            if not line.startswith('[server]'): continue;
            if 'match starting' in line.lower():
                match_started = True
                continue;
            if not match_started: continue
            if 'wins' in line.lower():
                a_won = int('(A)' in line)
                b_won = int('(B)' in line)
                break
            elif 'loses' in line.lower():
                a_won = 1 - int('(A)' in line)
                b_won = 1 - int('(B)' in line)
                break
    return (a_won, b_won)

async def run_game(a: str, b: str, map: str, ooo: int, sem: Semaphore):
    path = f"test/{map}{ooo}.txt"
    async with sem:
        command = "./gradlew run"\
            + f" -PteamA={a}"\
            + f" -PteamB={b}"\
            + f" -Pmaps={map}"\
            + f" -Psource=src"\
            + f" -PprofilerEnabled=false"\
            + f" -PoutputVerbose=false"\
        
        with open(path, "w") as f:
            process = await asyncio.create_subprocess_shell(
                command, shell=True, stdout=f, stderr=asyncio.subprocess.PIPE
            )
            await process.wait()
            f.flush()

    global history, a_wins, b_wins
    p1_won, p2_won = parse_results(path)
    a_wins += p2_won if (ooo) else p1_won
    b_wins += p1_won if (ooo) else p2_won
    print(f"{map:<15} -> {a if p1_won else b} wins!{' ' * 20}")
    print(f"{b if ooo else a} wins: {a_wins} | {a if ooo else b} wins: {b_wins}", end="\r", flush=True)
    os.remove(path)

async def play(a: str, b: str, maps: [str]):
    print(f"{a} wins: 0 | {b} wins: 0", end="\r", flush=True)

    random.shuffle(maps)
    sem = asyncio.Semaphore(8)

    tasks = []
    for i in range(len(maps)):
        tasks.append(run_game(a, b, maps[i], 0, sem))
        tasks.append(run_game(b, a, maps[i], 1, sem))
    await asyncio.gather(*tasks)

async def main():
    print("Starting Tests!")
    random.seed(42)
    if len(sys.argv) < 3:
        print("Usage: python3 tester.py [Player1] [Player2] [Maps]?")
        exit(1)
    _, a, b = sys.argv
    maps = sys.argv[3:] if len(sys.argv) >= 4 else read_maps()
    await play(a, b, maps)

asyncio.run(main())