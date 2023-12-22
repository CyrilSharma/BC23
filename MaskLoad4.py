# I have spent far too much time on this.

MAX_HEIGHT = 60
MAX_WIDTH = 60
FE_MASK_WIDTH = 9
FE_MASK_HEIGHT = 5
VISION = 9

indent = ' ' * 4

# 1. Map everything into a VISION by VISION box.
# 2. Use 16-bit addition to do assignments in 1 bytecode.
def printmask(name):
    tname = "t" + name
    # set of equivalence classes
    nums = [[] for i in range(VISION * VISION)]
    for y in range(MAX_HEIGHT):
        for x in range(MAX_WIDTH):
            idx = (y * VISION + x) % (VISION * VISION)
            nums[idx].append((y, x))

    print("switch (r.location.hashCode()) {")
    for idx in range(VISION * VISION):
        count = 0
        print(f"{indent}", end="")
        for (y, x) in nums[idx]:
            # print(f"case {(x << 16) + y}: ", end="")
            print(f"case {(1 << 16) * x + y + (1 << 15)}: ", end="")
            count += 1
            if (count != (len(nums[idx])) and count % 6 == 0):
                print()
                print(indent, end="")
        print()

        index = (idx) % (16)
        block = (idx) // 16;
        if (index < 15):
            print(f"{indent*2}{tname}{block} += {1 << index}; continue;")
        else:
            # The 15th bit cannot be used directly because of the sign-bit
            # Without special handling, this would compile into 4 vs. 2 bytecode.
            print(f"{indent*2}{tname}{block} += {1 << 14};")
            print(f"{indent*2}{tname}{block} += {1 << 14};")
            print(f"{indent*2}continue;")
    print(f"{indent}default: continue;")
    print("}")

def lshift(name, amount):
    assert amount >= 0
    if amount == 0: return f"{name}"
    else:           return f"{name} << {amount}"

def rshift(name, amount):
    assert amount >= 0
    if amount == 0: return f"{name}"
    else:           return f"{name} >>> {amount}"

# needs another mask to correct for overflowing into the top mask.
def fusemask(name):
    for block in range(((VISION * VISION) // 16) + 1):
        print(f"long l{name}{block} = t{name}{block};")

    cross_row = 5
    area = cross_row * FE_MASK_WIDTH
    offset = 64 - area
    crossover = 64
    bottommask = ((1 << 64) - 1) - ((1 << offset) - 1)
    topmask = (1 << (VISION * VISION - area)) - 1
    print("outer: switch (myloc.y) {")
    for ym in range(VISION):
        print(f"{indent}case {ym}: ", end="")
        for y in range(ym + VISION, MAX_HEIGHT, VISION):
            print(f"case {y}: ", end="")
        print()
        print(f"{indent * 2}switch (myloc.x) {{")
        for x in range(MAX_WIDTH):
            print(f"{indent * 3}case {x}: ", end="")
            BLX = (x - VISION // 2)
            BLY = (ym - VISION // 2)

            shift = ((VISION * VISION) + (BLY * VISION) + BLX) % (VISION * VISION)

            bshifts, tshifts = [], []
            for block in range(((VISION * VISION) // 16) + 1):
                rightmost = min(((block + 1) * 16) - 1, VISION * VISION - 1) # The tiling isn't perfect
                leftmost = block * 16
                bname = f"l{name}{block}"

                # If we can shift the entire mask up...
                if (VISION * VISION - rightmost >= shift):
                    nleftmost = offset + leftmost + shift
                    nrightmost = offset + rightmost + shift
                    if nleftmost < crossover and nrightmost < crossover:
                        bshifts.append(lshift(bname, nleftmost))
                    elif nleftmost >= crossover and nrightmost >= crossover:
                        tshifts.append(lshift(bname, nleftmost - crossover))
                    else:
                        bshifts.append(lshift(bname, nleftmost))
                        tshifts.append(rshift(bname, crossover - nleftmost))
                
                # If we can shift the entire mask down...
                elif (leftmost >= VISION * VISION - shift):
                    nshift = VISION * VISION - shift
                    nleftmost = offset + leftmost - nshift
                    nrightmost = offset + rightmost - nshift
                    if nleftmost < crossover and nrightmost < crossover:
                        bshifts.append(lshift(bname, nleftmost))
                    elif nleftmost >= crossover and nrightmost >= crossover:
                        tshifts.append(lshift(bname, nleftmost - crossover))
                    else:
                        bshifts.append(lshift(bname, nleftmost))
                        tshifts.append(rshift(bname, crossover - nleftmost))

                # Split the mask.
                else:
                    nshift = VISION * VISION - shift
                    bshifts.append(lshift(bname, offset + leftmost - nshift))
                    tshifts.append(lshift(bname, offset + leftmost + shift - crossover))

            print()
            parenthesized = [f"({shift})" for shift in bshifts]
            print(f"{indent * 4}{name}[0] = ({' | '.join(parenthesized)}) & {hex(bottommask)}L;")

            parenthesized = [f"({shift})" for shift in tshifts]
            print(f"{indent * 4}{name}[1] = ({' | '.join(parenthesized)}) & {hex(topmask)}L;")
            print(f"{indent * 4}break outer;")
        print(f"{indent * 3}default: break outer;")
        print(f"{indent * 2}}}")
    print(f"{indent}default: ")
    print("}")

printmask("friend_mask")
# printmask("enemy_mask")
# fusemask("friend_mask")
# fusemask("enemy_mask")
