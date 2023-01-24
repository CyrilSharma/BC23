# OG Source: https://github.com/IvanGeffner/battlecode2021/blob/master/thirtyone/BFSMuckraker.java
# Actual Source: https://github.com/2022tgoel/Battlecode2022/blob/master/build_nav.py
# Changes
# 1. Only look as passable or not passible.
# 2. Does not account for clouds and cooldowns bc they're not that important (yet).

import math
VISION = 20 # radius squared 
GRID = 9
CENTER = GRID*(GRID//2) + (GRID//2)
MAX = math.floor(math.sqrt(VISION))# max displacement in either x or y
NAME = "BFS"
print(MAX)
assert((GRID//2) >= MAX) # grid is large enough to incorporate entire vision radius

def numToLoc(num):
	return num%GRID, num//GRID

def distFromDroid(num):
	center = (GRID//2)
	x2, y2 = numToLoc(num)
	return (center-x2)**2 + (center-y2)**2

def numWithinRange(num):
	return distFromDroid(num) <= VISION;

nodes = []
def getNodes():
	for i in range(GRID**2):
		if numWithinRange(i):
			nodes.append(i)
	nodes.sort(key=distFromDroid)

getNodes()
print(f"Nodes: {nodes}")

deltas = [1, -1, -1*GRID, GRID, 1+GRID, -1+GRID, 1-GRID, -1-GRID] # possible deltas
deltaToDir= {1 : "Direction.EAST",
			-1 : "Direction.WEST",
			-1*GRID :"Direction.SOUTH" ,
			GRID :"Direction.NORTH",
			1+GRID :"Direction.NORTHEAST",
			-1+GRID :"Direction.NORTHWEST",
			1-GRID : "Direction.SOUTHEAST" ,
			-1-GRID : "Direction.SOUTHWEST" ,}

with open("{NAME}.java", "w") as f:
	def writeInstantiations(indent=1):
		s = "\t"*indent
		for n in nodes:
			f.write(f"{s}static MapLocation l{n};\n")
			f.write(f"{s}static double v{n};\n")
			f.write(f"{s}static Direction d{n};\n")
			f.write("\n")

	def writeValueSetting(indent=2):
		s = "\t"*indent
		visited = []
		for n in nodes:
			if (n == CENTER):
				f.write(f"{s}l{n} = rc.getLocation();\n")
				f.write(f"{s}v{n} = 0;\n")
			else:
				for prev in visited:
					if (n - prev) in deltas:
						f.write(f"{s}l{n} = l{prev}.add({deltaToDir[n - prev]});\n")
						break
				f.write(f"{s}v{n} = 1000000;\n")
				f.write(f"{s}d{n} = null;\n")
			visited.append(n)

	def writeEdgeRelaxation(node, prev, indent=4):
		s = "\t"*indent
		f.write(f"{s}if (v{node} > v{prev} + 1) {'{'}\n")
		f.write(f"{s}\tv{node} = v{prev} + 1;\n")
		if prev == CENTER:
			f.write(f"{s}\td{node} = {deltaToDir[node - prev]};\n")
		else:
			f.write(f"{s}\td{node} = d{prev};\n")
		f.write(f"{s}{'}'}\n")
		return; # temporary

	def writeNodeCalculation(node, visited, indent=2, ensure_unoccupied=True):
		if node == CENTER:
			return
		
		s = "\t"*indent
		f.write(f"{s}if (rc.onTheMap(l{node})) {'{'}\n")

		check_for_occupation = (node - CENTER in deltas) and ensure_unoccupied

		if check_for_occupation: 
			f.write(f"{s}\tif (!rc.isLocationOccupied(l{node}) && rc.sensePassability(l{node})) {'{'}\n")

		for prev in visited:
			if (node - prev) in deltas:
				writeEdgeRelaxation(node, prev)

		if check_for_occupation:
			f.write(f"{s}\t{'}'}\n")

		f.write(f"{s}{'}'}\n")

	def writeCasework(indent=2):
		s = "\t"*indent
		f.write(f"{s}int dx = target.x - l{CENTER}.x;\n")
		f.write(f"{s}int dy = target.y - l{CENTER}.y;\n")
		f.write(f"{s}switch (dx) {'{'}\n")
		for x in range(-1*MAX, MAX+1):
			f.write(f"{s}\tcase {x}:\n")
			f.write(f"{s}\t\tswitch (dy) {'{'}\n")
			for y in range(-1*MAX, MAX+1):
				num = (y + GRID//2)*GRID + (x+GRID//2)
				if (numWithinRange(num)):
					f.write(f"{s}\t\t\tcase {y}:\n")
					f.write(f"{s}\t\t\t\treturn d{num};\n")
					continue
			f.write(f"{s}\t\t{'}'}\n")
			f.write(f"{s}\t\tbreak;\n")
		f.write(f"{s}{'}'}\n")

	def writeHeuristicEstimation(indent=2):
		s = "\t"*indent
		f.write(f"{s}Direction ans = null;\n")
		f.write(f"{s}double bestEstimation = 0;\n")
		f.write(f"{s}double initialDist = Math.sqrt(l{CENTER}.distanceSquaredTo(target));\n")
		for n in nodes:
			isEdge = False
			for d in [1, -1, GRID, -1*GRID]:
				if not numWithinRange(n+d):
					isEdge = True
			if isEdge:
				f.write(f"{s}double dist{n} = (initialDist - Math.sqrt(l{n}.distanceSquaredTo(target))) / v{n};\n")
				f.write(f"{s}if (dist{n} > bestEstimation) {'{'}\n")
				f.write(f"{s}\tbestEstimation = dist{n};\n")
				f.write(f"{s}\tans = d{n};\n")
				f.write(f"{s}{'}'}\n")
		f.write(f"{s}return ans;\n")

	def writeFunction(ensure_unoccupied=True):
		fn_name = "getBestDir"
		f.write("\tDirection " + fn_name + "(MapLocation target) throws GameActionException{\n")
		writeValueSetting()
		visited = []
		for n in nodes:
			writeNodeCalculation(n, visited, ensure_unoccupied=ensure_unoccupied)
			visited.append(n)
		writeCasework()
		writeHeuristicEstimation()
		f.write("\t}\n")


	f.write("""
    import battlecode.common.*;
    public class BFS {
        static RobotController rc;
        static int cooldown;
        BFS(RobotController rc) {
            this.rc = rc;
        }
    """)

	writeInstantiations()
	f.write("\n")

	writeFunction()
	f.write("\n")

	f.write("}")