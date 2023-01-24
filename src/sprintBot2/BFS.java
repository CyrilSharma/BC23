package sprintBot2;
import battlecode.common.*;
public class BFS {
	static RobotController rc;
	static int cooldown;
	BFS(RobotController rc) {
		this.rc = rc;
	}
	static MapLocation l40;
	static double v40;
	static Direction d40;

	static MapLocation l31;
	static double v31;
	static Direction d31;

	static MapLocation l39;
	static double v39;
	static Direction d39;

	static MapLocation l41;
	static double v41;
	static Direction d41;

	static MapLocation l49;
	static double v49;
	static Direction d49;

	static MapLocation l30;
	static double v30;
	static Direction d30;

	static MapLocation l32;
	static double v32;
	static Direction d32;

	static MapLocation l48;
	static double v48;
	static Direction d48;

	static MapLocation l50;
	static double v50;
	static Direction d50;

	static MapLocation l22;
	static double v22;
	static Direction d22;

	static MapLocation l38;
	static double v38;
	static Direction d38;

	static MapLocation l42;
	static double v42;
	static Direction d42;

	static MapLocation l58;
	static double v58;
	static Direction d58;

	static MapLocation l21;
	static double v21;
	static Direction d21;

	static MapLocation l23;
	static double v23;
	static Direction d23;

	static MapLocation l29;
	static double v29;
	static Direction d29;

	static MapLocation l33;
	static double v33;
	static Direction d33;

	static MapLocation l47;
	static double v47;
	static Direction d47;

	static MapLocation l51;
	static double v51;
	static Direction d51;

	static MapLocation l57;
	static double v57;
	static Direction d57;

	static MapLocation l59;
	static double v59;
	static Direction d59;

	static MapLocation l20;
	static double v20;
	static Direction d20;

	static MapLocation l24;
	static double v24;
	static Direction d24;

	static MapLocation l56;
	static double v56;
	static Direction d56;

	static MapLocation l60;
	static double v60;
	static Direction d60;

	static MapLocation l13;
	static double v13;
	static Direction d13;

	static MapLocation l37;
	static double v37;
	static Direction d37;

	static MapLocation l43;
	static double v43;
	static Direction d43;

	static MapLocation l67;
	static double v67;
	static Direction d67;

	static MapLocation l12;
	static double v12;
	static Direction d12;

	static MapLocation l14;
	static double v14;
	static Direction d14;

	static MapLocation l28;
	static double v28;
	static Direction d28;

	static MapLocation l34;
	static double v34;
	static Direction d34;

	static MapLocation l46;
	static double v46;
	static Direction d46;

	static MapLocation l52;
	static double v52;
	static Direction d52;

	static MapLocation l66;
	static double v66;
	static Direction d66;

	static MapLocation l68;
	static double v68;
	static Direction d68;

	static MapLocation l11;
	static double v11;
	static Direction d11;

	static MapLocation l15;
	static double v15;
	static Direction d15;

	static MapLocation l19;
	static double v19;
	static Direction d19;

	static MapLocation l25;
	static double v25;
	static Direction d25;

	static MapLocation l55;
	static double v55;
	static Direction d55;

	static MapLocation l61;
	static double v61;
	static Direction d61;

	static MapLocation l65;
	static double v65;
	static Direction d65;

	static MapLocation l69;
	static double v69;
	static Direction d69;

	static MapLocation l4;
	static double v4;
	static Direction d4;

	static MapLocation l36;
	static double v36;
	static Direction d36;

	static MapLocation l44;
	static double v44;
	static Direction d44;

	static MapLocation l76;
	static double v76;
	static Direction d76;

	static MapLocation l3;
	static double v3;
	static Direction d3;

	static MapLocation l5;
	static double v5;
	static Direction d5;

	static MapLocation l27;
	static double v27;
	static Direction d27;

	static MapLocation l35;
	static double v35;
	static Direction d35;

	static MapLocation l45;
	static double v45;
	static Direction d45;

	static MapLocation l53;
	static double v53;
	static Direction d53;

	static MapLocation l75;
	static double v75;
	static Direction d75;

	static MapLocation l77;
	static double v77;
	static Direction d77;

	static MapLocation l10;
	static double v10;
	static Direction d10;

	static MapLocation l16;
	static double v16;
	static Direction d16;

	static MapLocation l64;
	static double v64;
	static Direction d64;

	static MapLocation l70;
	static double v70;
	static Direction d70;

	static MapLocation l2;
	static double v2;
	static Direction d2;

	static MapLocation l6;
	static double v6;
	static Direction d6;

	static MapLocation l18;
	static double v18;
	static Direction d18;

	static MapLocation l26;
	static double v26;
	static Direction d26;

	static MapLocation l54;
	static double v54;
	static Direction d54;

	static MapLocation l62;
	static double v62;
	static Direction d62;

	static MapLocation l74;
	static double v74;
	static Direction d74;

	static MapLocation l78;
	static double v78;
	static Direction d78;


	Direction getBestDir(MapLocation target) throws GameActionException{
		l40 = rc.getLocation();
		v40 = 0;
		l31 = l40.add(Direction.SOUTH);
		v31 = 1000000;
		d31 = null;
		l39 = l40.add(Direction.WEST);
		v39 = 1000000;
		d39 = null;
		l41 = l40.add(Direction.EAST);
		v41 = 1000000;
		d41 = null;
		l49 = l40.add(Direction.NORTH);
		v49 = 1000000;
		d49 = null;
		l30 = l40.add(Direction.SOUTHWEST);
		v30 = 1000000;
		d30 = null;
		l32 = l40.add(Direction.SOUTHEAST);
		v32 = 1000000;
		d32 = null;
		l48 = l40.add(Direction.NORTHWEST);
		v48 = 1000000;
		d48 = null;
		l50 = l40.add(Direction.NORTHEAST);
		v50 = 1000000;
		d50 = null;
		l22 = l31.add(Direction.SOUTH);
		v22 = 1000000;
		d22 = null;
		l38 = l39.add(Direction.WEST);
		v38 = 1000000;
		d38 = null;
		l42 = l41.add(Direction.EAST);
		v42 = 1000000;
		d42 = null;
		l58 = l49.add(Direction.NORTH);
		v58 = 1000000;
		d58 = null;
		l21 = l31.add(Direction.SOUTHWEST);
		v21 = 1000000;
		d21 = null;
		l23 = l31.add(Direction.SOUTHEAST);
		v23 = 1000000;
		d23 = null;
		l29 = l39.add(Direction.SOUTHWEST);
		v29 = 1000000;
		d29 = null;
		l33 = l41.add(Direction.SOUTHEAST);
		v33 = 1000000;
		d33 = null;
		l47 = l39.add(Direction.NORTHWEST);
		v47 = 1000000;
		d47 = null;
		l51 = l41.add(Direction.NORTHEAST);
		v51 = 1000000;
		d51 = null;
		l57 = l49.add(Direction.NORTHWEST);
		v57 = 1000000;
		d57 = null;
		l59 = l49.add(Direction.NORTHEAST);
		v59 = 1000000;
		d59 = null;
		l20 = l30.add(Direction.SOUTHWEST);
		v20 = 1000000;
		d20 = null;
		l24 = l32.add(Direction.SOUTHEAST);
		v24 = 1000000;
		d24 = null;
		l56 = l48.add(Direction.NORTHWEST);
		v56 = 1000000;
		d56 = null;
		l60 = l50.add(Direction.NORTHEAST);
		v60 = 1000000;
		d60 = null;
		l13 = l22.add(Direction.SOUTH);
		v13 = 1000000;
		d13 = null;
		l37 = l38.add(Direction.WEST);
		v37 = 1000000;
		d37 = null;
		l43 = l42.add(Direction.EAST);
		v43 = 1000000;
		d43 = null;
		l67 = l58.add(Direction.NORTH);
		v67 = 1000000;
		d67 = null;
		l12 = l22.add(Direction.SOUTHWEST);
		v12 = 1000000;
		d12 = null;
		l14 = l22.add(Direction.SOUTHEAST);
		v14 = 1000000;
		d14 = null;
		l28 = l38.add(Direction.SOUTHWEST);
		v28 = 1000000;
		d28 = null;
		l34 = l42.add(Direction.SOUTHEAST);
		v34 = 1000000;
		d34 = null;
		l46 = l38.add(Direction.NORTHWEST);
		v46 = 1000000;
		d46 = null;
		l52 = l42.add(Direction.NORTHEAST);
		v52 = 1000000;
		d52 = null;
		l66 = l58.add(Direction.NORTHWEST);
		v66 = 1000000;
		d66 = null;
		l68 = l58.add(Direction.NORTHEAST);
		v68 = 1000000;
		d68 = null;
		l11 = l21.add(Direction.SOUTHWEST);
		v11 = 1000000;
		d11 = null;
		l15 = l23.add(Direction.SOUTHEAST);
		v15 = 1000000;
		d15 = null;
		l19 = l29.add(Direction.SOUTHWEST);
		v19 = 1000000;
		d19 = null;
		l25 = l33.add(Direction.SOUTHEAST);
		v25 = 1000000;
		d25 = null;
		l55 = l47.add(Direction.NORTHWEST);
		v55 = 1000000;
		d55 = null;
		l61 = l51.add(Direction.NORTHEAST);
		v61 = 1000000;
		d61 = null;
		l65 = l57.add(Direction.NORTHWEST);
		v65 = 1000000;
		d65 = null;
		l69 = l59.add(Direction.NORTHEAST);
		v69 = 1000000;
		d69 = null;
		l4 = l13.add(Direction.SOUTH);
		v4 = 1000000;
		d4 = null;
		l36 = l37.add(Direction.WEST);
		v36 = 1000000;
		d36 = null;
		l44 = l43.add(Direction.EAST);
		v44 = 1000000;
		d44 = null;
		l76 = l67.add(Direction.NORTH);
		v76 = 1000000;
		d76 = null;
		l3 = l13.add(Direction.SOUTHWEST);
		v3 = 1000000;
		d3 = null;
		l5 = l13.add(Direction.SOUTHEAST);
		v5 = 1000000;
		d5 = null;
		l27 = l37.add(Direction.SOUTHWEST);
		v27 = 1000000;
		d27 = null;
		l35 = l43.add(Direction.SOUTHEAST);
		v35 = 1000000;
		d35 = null;
		l45 = l37.add(Direction.NORTHWEST);
		v45 = 1000000;
		d45 = null;
		l53 = l43.add(Direction.NORTHEAST);
		v53 = 1000000;
		d53 = null;
		l75 = l67.add(Direction.NORTHWEST);
		v75 = 1000000;
		d75 = null;
		l77 = l67.add(Direction.NORTHEAST);
		v77 = 1000000;
		d77 = null;
		l10 = l20.add(Direction.SOUTHWEST);
		v10 = 1000000;
		d10 = null;
		l16 = l24.add(Direction.SOUTHEAST);
		v16 = 1000000;
		d16 = null;
		l64 = l56.add(Direction.NORTHWEST);
		v64 = 1000000;
		d64 = null;
		l70 = l60.add(Direction.NORTHEAST);
		v70 = 1000000;
		d70 = null;
		l2 = l12.add(Direction.SOUTHWEST);
		v2 = 1000000;
		d2 = null;
		l6 = l14.add(Direction.SOUTHEAST);
		v6 = 1000000;
		d6 = null;
		l18 = l28.add(Direction.SOUTHWEST);
		v18 = 1000000;
		d18 = null;
		l26 = l34.add(Direction.SOUTHEAST);
		v26 = 1000000;
		d26 = null;
		l54 = l46.add(Direction.NORTHWEST);
		v54 = 1000000;
		d54 = null;
		l62 = l52.add(Direction.NORTHEAST);
		v62 = 1000000;
		d62 = null;
		l74 = l66.add(Direction.NORTHWEST);
		v74 = 1000000;
		d74 = null;
		l78 = l68.add(Direction.NORTHEAST);
		v78 = 1000000;
		d78 = null;
		if (rc.onTheMap(l31)) {
			if (!rc.isLocationOccupied(l31) && rc.sensePassability(l31)) {
				if (v31 > v40 + 1) {
					v31 = v40 + 1;
					d31 = Direction.SOUTH;
				}
			}
		}
		if (rc.onTheMap(l39)) {
			if (!rc.isLocationOccupied(l39) && rc.sensePassability(l39)) {
				if (v39 > v40 + 1) {
					v39 = v40 + 1;
					d39 = Direction.WEST;
				}
				if (v39 > v31 + 1) {
					v39 = v31 + 1;
					d39 = d31;
				}
			}
		}
		if (rc.onTheMap(l41)) {
			if (!rc.isLocationOccupied(l41) && rc.sensePassability(l41)) {
				if (v41 > v40 + 1) {
					v41 = v40 + 1;
					d41 = Direction.EAST;
				}
				if (v41 > v31 + 1) {
					v41 = v31 + 1;
					d41 = d31;
				}
			}
		}
		if (rc.onTheMap(l49)) {
			if (!rc.isLocationOccupied(l49) && rc.sensePassability(l49)) {
				if (v49 > v40 + 1) {
					v49 = v40 + 1;
					d49 = Direction.NORTH;
				}
				if (v49 > v39 + 1) {
					v49 = v39 + 1;
					d49 = d39;
				}
				if (v49 > v41 + 1) {
					v49 = v41 + 1;
					d49 = d41;
				}
			}
		}
		if (rc.onTheMap(l30)) {
			if (!rc.isLocationOccupied(l30) && rc.sensePassability(l30)) {
				if (v30 > v40 + 1) {
					v30 = v40 + 1;
					d30 = Direction.SOUTHWEST;
				}
				if (v30 > v31 + 1) {
					v30 = v31 + 1;
					d30 = d31;
				}
				if (v30 > v39 + 1) {
					v30 = v39 + 1;
					d30 = d39;
				}
			}
		}
		if (rc.onTheMap(l32)) {
			if (!rc.isLocationOccupied(l32) && rc.sensePassability(l32)) {
				if (v32 > v40 + 1) {
					v32 = v40 + 1;
					d32 = Direction.SOUTHEAST;
				}
				if (v32 > v31 + 1) {
					v32 = v31 + 1;
					d32 = d31;
				}
				if (v32 > v41 + 1) {
					v32 = v41 + 1;
					d32 = d41;
				}
			}
		}
		if (rc.onTheMap(l48)) {
			if (!rc.isLocationOccupied(l48) && rc.sensePassability(l48)) {
				if (v48 > v40 + 1) {
					v48 = v40 + 1;
					d48 = Direction.NORTHWEST;
				}
				if (v48 > v39 + 1) {
					v48 = v39 + 1;
					d48 = d39;
				}
				if (v48 > v49 + 1) {
					v48 = v49 + 1;
					d48 = d49;
				}
			}
		}
		if (rc.onTheMap(l50)) {
			if (!rc.isLocationOccupied(l50) && rc.sensePassability(l50)) {
				if (v50 > v40 + 1) {
					v50 = v40 + 1;
					d50 = Direction.NORTHEAST;
				}
				if (v50 > v41 + 1) {
					v50 = v41 + 1;
					d50 = d41;
				}
				if (v50 > v49 + 1) {
					v50 = v49 + 1;
					d50 = d49;
				}
			}
		}
		if (rc.onTheMap(l22)) {
				if (v22 > v31 + 1) {
					v22 = v31 + 1;
					d22 = d31;
				}
				if (v22 > v30 + 1) {
					v22 = v30 + 1;
					d22 = d30;
				}
				if (v22 > v32 + 1) {
					v22 = v32 + 1;
					d22 = d32;
				}
		}
		if (rc.onTheMap(l38)) {
				if (v38 > v39 + 1) {
					v38 = v39 + 1;
					d38 = d39;
				}
				if (v38 > v30 + 1) {
					v38 = v30 + 1;
					d38 = d30;
				}
				if (v38 > v48 + 1) {
					v38 = v48 + 1;
					d38 = d48;
				}
		}
		if (rc.onTheMap(l42)) {
				if (v42 > v41 + 1) {
					v42 = v41 + 1;
					d42 = d41;
				}
				if (v42 > v32 + 1) {
					v42 = v32 + 1;
					d42 = d32;
				}
				if (v42 > v50 + 1) {
					v42 = v50 + 1;
					d42 = d50;
				}
		}
		if (rc.onTheMap(l58)) {
				if (v58 > v49 + 1) {
					v58 = v49 + 1;
					d58 = d49;
				}
				if (v58 > v48 + 1) {
					v58 = v48 + 1;
					d58 = d48;
				}
				if (v58 > v50 + 1) {
					v58 = v50 + 1;
					d58 = d50;
				}
		}
		if (rc.onTheMap(l21)) {
				if (v21 > v31 + 1) {
					v21 = v31 + 1;
					d21 = d31;
				}
				if (v21 > v30 + 1) {
					v21 = v30 + 1;
					d21 = d30;
				}
				if (v21 > v22 + 1) {
					v21 = v22 + 1;
					d21 = d22;
				}
		}
		if (rc.onTheMap(l23)) {
				if (v23 > v31 + 1) {
					v23 = v31 + 1;
					d23 = d31;
				}
				if (v23 > v32 + 1) {
					v23 = v32 + 1;
					d23 = d32;
				}
				if (v23 > v22 + 1) {
					v23 = v22 + 1;
					d23 = d22;
				}
		}
		if (rc.onTheMap(l29)) {
				if (v29 > v39 + 1) {
					v29 = v39 + 1;
					d29 = d39;
				}
				if (v29 > v30 + 1) {
					v29 = v30 + 1;
					d29 = d30;
				}
				if (v29 > v38 + 1) {
					v29 = v38 + 1;
					d29 = d38;
				}
				if (v29 > v21 + 1) {
					v29 = v21 + 1;
					d29 = d21;
				}
		}
		if (rc.onTheMap(l33)) {
				if (v33 > v41 + 1) {
					v33 = v41 + 1;
					d33 = d41;
				}
				if (v33 > v32 + 1) {
					v33 = v32 + 1;
					d33 = d32;
				}
				if (v33 > v42 + 1) {
					v33 = v42 + 1;
					d33 = d42;
				}
				if (v33 > v23 + 1) {
					v33 = v23 + 1;
					d33 = d23;
				}
		}
		if (rc.onTheMap(l47)) {
				if (v47 > v39 + 1) {
					v47 = v39 + 1;
					d47 = d39;
				}
				if (v47 > v48 + 1) {
					v47 = v48 + 1;
					d47 = d48;
				}
				if (v47 > v38 + 1) {
					v47 = v38 + 1;
					d47 = d38;
				}
		}
		if (rc.onTheMap(l51)) {
				if (v51 > v41 + 1) {
					v51 = v41 + 1;
					d51 = d41;
				}
				if (v51 > v50 + 1) {
					v51 = v50 + 1;
					d51 = d50;
				}
				if (v51 > v42 + 1) {
					v51 = v42 + 1;
					d51 = d42;
				}
		}
		if (rc.onTheMap(l57)) {
				if (v57 > v49 + 1) {
					v57 = v49 + 1;
					d57 = d49;
				}
				if (v57 > v48 + 1) {
					v57 = v48 + 1;
					d57 = d48;
				}
				if (v57 > v58 + 1) {
					v57 = v58 + 1;
					d57 = d58;
				}
				if (v57 > v47 + 1) {
					v57 = v47 + 1;
					d57 = d47;
				}
		}
		if (rc.onTheMap(l59)) {
				if (v59 > v49 + 1) {
					v59 = v49 + 1;
					d59 = d49;
				}
				if (v59 > v50 + 1) {
					v59 = v50 + 1;
					d59 = d50;
				}
				if (v59 > v58 + 1) {
					v59 = v58 + 1;
					d59 = d58;
				}
				if (v59 > v51 + 1) {
					v59 = v51 + 1;
					d59 = d51;
				}
		}
		if (rc.onTheMap(l20)) {
				if (v20 > v30 + 1) {
					v20 = v30 + 1;
					d20 = d30;
				}
				if (v20 > v21 + 1) {
					v20 = v21 + 1;
					d20 = d21;
				}
				if (v20 > v29 + 1) {
					v20 = v29 + 1;
					d20 = d29;
				}
		}
		if (rc.onTheMap(l24)) {
				if (v24 > v32 + 1) {
					v24 = v32 + 1;
					d24 = d32;
				}
				if (v24 > v23 + 1) {
					v24 = v23 + 1;
					d24 = d23;
				}
				if (v24 > v33 + 1) {
					v24 = v33 + 1;
					d24 = d33;
				}
		}
		if (rc.onTheMap(l56)) {
				if (v56 > v48 + 1) {
					v56 = v48 + 1;
					d56 = d48;
				}
				if (v56 > v47 + 1) {
					v56 = v47 + 1;
					d56 = d47;
				}
				if (v56 > v57 + 1) {
					v56 = v57 + 1;
					d56 = d57;
				}
		}
		if (rc.onTheMap(l60)) {
				if (v60 > v50 + 1) {
					v60 = v50 + 1;
					d60 = d50;
				}
				if (v60 > v51 + 1) {
					v60 = v51 + 1;
					d60 = d51;
				}
				if (v60 > v59 + 1) {
					v60 = v59 + 1;
					d60 = d59;
				}
		}
		if (rc.onTheMap(l13)) {
				if (v13 > v22 + 1) {
					v13 = v22 + 1;
					d13 = d22;
				}
				if (v13 > v21 + 1) {
					v13 = v21 + 1;
					d13 = d21;
				}
				if (v13 > v23 + 1) {
					v13 = v23 + 1;
					d13 = d23;
				}
		}
		if (rc.onTheMap(l37)) {
				if (v37 > v38 + 1) {
					v37 = v38 + 1;
					d37 = d38;
				}
				if (v37 > v29 + 1) {
					v37 = v29 + 1;
					d37 = d29;
				}
				if (v37 > v47 + 1) {
					v37 = v47 + 1;
					d37 = d47;
				}
		}
		if (rc.onTheMap(l43)) {
				if (v43 > v42 + 1) {
					v43 = v42 + 1;
					d43 = d42;
				}
				if (v43 > v33 + 1) {
					v43 = v33 + 1;
					d43 = d33;
				}
				if (v43 > v51 + 1) {
					v43 = v51 + 1;
					d43 = d51;
				}
		}
		if (rc.onTheMap(l67)) {
				if (v67 > v58 + 1) {
					v67 = v58 + 1;
					d67 = d58;
				}
				if (v67 > v57 + 1) {
					v67 = v57 + 1;
					d67 = d57;
				}
				if (v67 > v59 + 1) {
					v67 = v59 + 1;
					d67 = d59;
				}
		}
		if (rc.onTheMap(l12)) {
				if (v12 > v22 + 1) {
					v12 = v22 + 1;
					d12 = d22;
				}
				if (v12 > v21 + 1) {
					v12 = v21 + 1;
					d12 = d21;
				}
				if (v12 > v20 + 1) {
					v12 = v20 + 1;
					d12 = d20;
				}
				if (v12 > v13 + 1) {
					v12 = v13 + 1;
					d12 = d13;
				}
		}
		if (rc.onTheMap(l14)) {
				if (v14 > v22 + 1) {
					v14 = v22 + 1;
					d14 = d22;
				}
				if (v14 > v23 + 1) {
					v14 = v23 + 1;
					d14 = d23;
				}
				if (v14 > v24 + 1) {
					v14 = v24 + 1;
					d14 = d24;
				}
				if (v14 > v13 + 1) {
					v14 = v13 + 1;
					d14 = d13;
				}
		}
		if (rc.onTheMap(l28)) {
				if (v28 > v38 + 1) {
					v28 = v38 + 1;
					d28 = d38;
				}
				if (v28 > v29 + 1) {
					v28 = v29 + 1;
					d28 = d29;
				}
				if (v28 > v20 + 1) {
					v28 = v20 + 1;
					d28 = d20;
				}
				if (v28 > v37 + 1) {
					v28 = v37 + 1;
					d28 = d37;
				}
		}
		if (rc.onTheMap(l34)) {
				if (v34 > v42 + 1) {
					v34 = v42 + 1;
					d34 = d42;
				}
				if (v34 > v33 + 1) {
					v34 = v33 + 1;
					d34 = d33;
				}
				if (v34 > v24 + 1) {
					v34 = v24 + 1;
					d34 = d24;
				}
				if (v34 > v43 + 1) {
					v34 = v43 + 1;
					d34 = d43;
				}
		}
		if (rc.onTheMap(l46)) {
				if (v46 > v38 + 1) {
					v46 = v38 + 1;
					d46 = d38;
				}
				if (v46 > v47 + 1) {
					v46 = v47 + 1;
					d46 = d47;
				}
				if (v46 > v56 + 1) {
					v46 = v56 + 1;
					d46 = d56;
				}
				if (v46 > v37 + 1) {
					v46 = v37 + 1;
					d46 = d37;
				}
		}
		if (rc.onTheMap(l52)) {
				if (v52 > v42 + 1) {
					v52 = v42 + 1;
					d52 = d42;
				}
				if (v52 > v51 + 1) {
					v52 = v51 + 1;
					d52 = d51;
				}
				if (v52 > v60 + 1) {
					v52 = v60 + 1;
					d52 = d60;
				}
				if (v52 > v43 + 1) {
					v52 = v43 + 1;
					d52 = d43;
				}
		}
		if (rc.onTheMap(l66)) {
				if (v66 > v58 + 1) {
					v66 = v58 + 1;
					d66 = d58;
				}
				if (v66 > v57 + 1) {
					v66 = v57 + 1;
					d66 = d57;
				}
				if (v66 > v56 + 1) {
					v66 = v56 + 1;
					d66 = d56;
				}
				if (v66 > v67 + 1) {
					v66 = v67 + 1;
					d66 = d67;
				}
		}
		if (rc.onTheMap(l68)) {
				if (v68 > v58 + 1) {
					v68 = v58 + 1;
					d68 = d58;
				}
				if (v68 > v59 + 1) {
					v68 = v59 + 1;
					d68 = d59;
				}
				if (v68 > v60 + 1) {
					v68 = v60 + 1;
					d68 = d60;
				}
				if (v68 > v67 + 1) {
					v68 = v67 + 1;
					d68 = d67;
				}
		}
		if (rc.onTheMap(l11)) {
				if (v11 > v21 + 1) {
					v11 = v21 + 1;
					d11 = d21;
				}
				if (v11 > v20 + 1) {
					v11 = v20 + 1;
					d11 = d20;
				}
				if (v11 > v12 + 1) {
					v11 = v12 + 1;
					d11 = d12;
				}
		}
		if (rc.onTheMap(l15)) {
				if (v15 > v23 + 1) {
					v15 = v23 + 1;
					d15 = d23;
				}
				if (v15 > v24 + 1) {
					v15 = v24 + 1;
					d15 = d24;
				}
				if (v15 > v14 + 1) {
					v15 = v14 + 1;
					d15 = d14;
				}
		}
		if (rc.onTheMap(l19)) {
				if (v19 > v29 + 1) {
					v19 = v29 + 1;
					d19 = d29;
				}
				if (v19 > v20 + 1) {
					v19 = v20 + 1;
					d19 = d20;
				}
				if (v19 > v28 + 1) {
					v19 = v28 + 1;
					d19 = d28;
				}
				if (v19 > v11 + 1) {
					v19 = v11 + 1;
					d19 = d11;
				}
		}
		if (rc.onTheMap(l25)) {
				if (v25 > v33 + 1) {
					v25 = v33 + 1;
					d25 = d33;
				}
				if (v25 > v24 + 1) {
					v25 = v24 + 1;
					d25 = d24;
				}
				if (v25 > v34 + 1) {
					v25 = v34 + 1;
					d25 = d34;
				}
				if (v25 > v15 + 1) {
					v25 = v15 + 1;
					d25 = d15;
				}
		}
		if (rc.onTheMap(l55)) {
				if (v55 > v47 + 1) {
					v55 = v47 + 1;
					d55 = d47;
				}
				if (v55 > v56 + 1) {
					v55 = v56 + 1;
					d55 = d56;
				}
				if (v55 > v46 + 1) {
					v55 = v46 + 1;
					d55 = d46;
				}
		}
		if (rc.onTheMap(l61)) {
				if (v61 > v51 + 1) {
					v61 = v51 + 1;
					d61 = d51;
				}
				if (v61 > v60 + 1) {
					v61 = v60 + 1;
					d61 = d60;
				}
				if (v61 > v52 + 1) {
					v61 = v52 + 1;
					d61 = d52;
				}
		}
		if (rc.onTheMap(l65)) {
				if (v65 > v57 + 1) {
					v65 = v57 + 1;
					d65 = d57;
				}
				if (v65 > v56 + 1) {
					v65 = v56 + 1;
					d65 = d56;
				}
				if (v65 > v66 + 1) {
					v65 = v66 + 1;
					d65 = d66;
				}
				if (v65 > v55 + 1) {
					v65 = v55 + 1;
					d65 = d55;
				}
		}
		if (rc.onTheMap(l69)) {
				if (v69 > v59 + 1) {
					v69 = v59 + 1;
					d69 = d59;
				}
				if (v69 > v60 + 1) {
					v69 = v60 + 1;
					d69 = d60;
				}
				if (v69 > v68 + 1) {
					v69 = v68 + 1;
					d69 = d68;
				}
				if (v69 > v61 + 1) {
					v69 = v61 + 1;
					d69 = d61;
				}
		}
		if (rc.onTheMap(l4)) {
				if (v4 > v13 + 1) {
					v4 = v13 + 1;
					d4 = d13;
				}
				if (v4 > v12 + 1) {
					v4 = v12 + 1;
					d4 = d12;
				}
				if (v4 > v14 + 1) {
					v4 = v14 + 1;
					d4 = d14;
				}
		}
		if (rc.onTheMap(l36)) {
				if (v36 > v37 + 1) {
					v36 = v37 + 1;
					d36 = d37;
				}
				if (v36 > v28 + 1) {
					v36 = v28 + 1;
					d36 = d28;
				}
				if (v36 > v46 + 1) {
					v36 = v46 + 1;
					d36 = d46;
				}
		}
		if (rc.onTheMap(l44)) {
				if (v44 > v43 + 1) {
					v44 = v43 + 1;
					d44 = d43;
				}
				if (v44 > v34 + 1) {
					v44 = v34 + 1;
					d44 = d34;
				}
				if (v44 > v52 + 1) {
					v44 = v52 + 1;
					d44 = d52;
				}
				if (v44 > v36 + 1) {
					v44 = v36 + 1;
					d44 = d36;
				}
		}
		if (rc.onTheMap(l76)) {
				if (v76 > v67 + 1) {
					v76 = v67 + 1;
					d76 = d67;
				}
				if (v76 > v66 + 1) {
					v76 = v66 + 1;
					d76 = d66;
				}
				if (v76 > v68 + 1) {
					v76 = v68 + 1;
					d76 = d68;
				}
		}
		if (rc.onTheMap(l3)) {
				if (v3 > v13 + 1) {
					v3 = v13 + 1;
					d3 = d13;
				}
				if (v3 > v12 + 1) {
					v3 = v12 + 1;
					d3 = d12;
				}
				if (v3 > v11 + 1) {
					v3 = v11 + 1;
					d3 = d11;
				}
				if (v3 > v4 + 1) {
					v3 = v4 + 1;
					d3 = d4;
				}
		}
		if (rc.onTheMap(l5)) {
				if (v5 > v13 + 1) {
					v5 = v13 + 1;
					d5 = d13;
				}
				if (v5 > v14 + 1) {
					v5 = v14 + 1;
					d5 = d14;
				}
				if (v5 > v15 + 1) {
					v5 = v15 + 1;
					d5 = d15;
				}
				if (v5 > v4 + 1) {
					v5 = v4 + 1;
					d5 = d4;
				}
		}
		if (rc.onTheMap(l27)) {
				if (v27 > v37 + 1) {
					v27 = v37 + 1;
					d27 = d37;
				}
				if (v27 > v28 + 1) {
					v27 = v28 + 1;
					d27 = d28;
				}
				if (v27 > v19 + 1) {
					v27 = v19 + 1;
					d27 = d19;
				}
				if (v27 > v36 + 1) {
					v27 = v36 + 1;
					d27 = d36;
				}
		}
		if (rc.onTheMap(l35)) {
				if (v35 > v43 + 1) {
					v35 = v43 + 1;
					d35 = d43;
				}
				if (v35 > v34 + 1) {
					v35 = v34 + 1;
					d35 = d34;
				}
				if (v35 > v25 + 1) {
					v35 = v25 + 1;
					d35 = d25;
				}
				if (v35 > v36 + 1) {
					v35 = v36 + 1;
					d35 = d36;
				}
				if (v35 > v44 + 1) {
					v35 = v44 + 1;
					d35 = d44;
				}
				if (v35 > v27 + 1) {
					v35 = v27 + 1;
					d35 = d27;
				}
		}
		if (rc.onTheMap(l45)) {
				if (v45 > v37 + 1) {
					v45 = v37 + 1;
					d45 = d37;
				}
				if (v45 > v46 + 1) {
					v45 = v46 + 1;
					d45 = d46;
				}
				if (v45 > v55 + 1) {
					v45 = v55 + 1;
					d45 = d55;
				}
				if (v45 > v36 + 1) {
					v45 = v36 + 1;
					d45 = d36;
				}
				if (v45 > v44 + 1) {
					v45 = v44 + 1;
					d45 = d44;
				}
				if (v45 > v35 + 1) {
					v45 = v35 + 1;
					d45 = d35;
				}
		}
		if (rc.onTheMap(l53)) {
				if (v53 > v43 + 1) {
					v53 = v43 + 1;
					d53 = d43;
				}
				if (v53 > v52 + 1) {
					v53 = v52 + 1;
					d53 = d52;
				}
				if (v53 > v61 + 1) {
					v53 = v61 + 1;
					d53 = d61;
				}
				if (v53 > v44 + 1) {
					v53 = v44 + 1;
					d53 = d44;
				}
				if (v53 > v45 + 1) {
					v53 = v45 + 1;
					d53 = d45;
				}
		}
		if (rc.onTheMap(l75)) {
				if (v75 > v67 + 1) {
					v75 = v67 + 1;
					d75 = d67;
				}
				if (v75 > v66 + 1) {
					v75 = v66 + 1;
					d75 = d66;
				}
				if (v75 > v65 + 1) {
					v75 = v65 + 1;
					d75 = d65;
				}
				if (v75 > v76 + 1) {
					v75 = v76 + 1;
					d75 = d76;
				}
		}
		if (rc.onTheMap(l77)) {
				if (v77 > v67 + 1) {
					v77 = v67 + 1;
					d77 = d67;
				}
				if (v77 > v68 + 1) {
					v77 = v68 + 1;
					d77 = d68;
				}
				if (v77 > v69 + 1) {
					v77 = v69 + 1;
					d77 = d69;
				}
				if (v77 > v76 + 1) {
					v77 = v76 + 1;
					d77 = d76;
				}
		}
		if (rc.onTheMap(l10)) {
				if (v10 > v20 + 1) {
					v10 = v20 + 1;
					d10 = d20;
				}
				if (v10 > v11 + 1) {
					v10 = v11 + 1;
					d10 = d11;
				}
				if (v10 > v19 + 1) {
					v10 = v19 + 1;
					d10 = d19;
				}
		}
		if (rc.onTheMap(l16)) {
				if (v16 > v24 + 1) {
					v16 = v24 + 1;
					d16 = d24;
				}
				if (v16 > v15 + 1) {
					v16 = v15 + 1;
					d16 = d15;
				}
				if (v16 > v25 + 1) {
					v16 = v25 + 1;
					d16 = d25;
				}
		}
		if (rc.onTheMap(l64)) {
				if (v64 > v56 + 1) {
					v64 = v56 + 1;
					d64 = d56;
				}
				if (v64 > v55 + 1) {
					v64 = v55 + 1;
					d64 = d55;
				}
				if (v64 > v65 + 1) {
					v64 = v65 + 1;
					d64 = d65;
				}
		}
		if (rc.onTheMap(l70)) {
				if (v70 > v60 + 1) {
					v70 = v60 + 1;
					d70 = d60;
				}
				if (v70 > v61 + 1) {
					v70 = v61 + 1;
					d70 = d61;
				}
				if (v70 > v69 + 1) {
					v70 = v69 + 1;
					d70 = d69;
				}
		}
		if (rc.onTheMap(l2)) {
				if (v2 > v12 + 1) {
					v2 = v12 + 1;
					d2 = d12;
				}
				if (v2 > v11 + 1) {
					v2 = v11 + 1;
					d2 = d11;
				}
				if (v2 > v3 + 1) {
					v2 = v3 + 1;
					d2 = d3;
				}
				if (v2 > v10 + 1) {
					v2 = v10 + 1;
					d2 = d10;
				}
		}
		if (rc.onTheMap(l6)) {
				if (v6 > v14 + 1) {
					v6 = v14 + 1;
					d6 = d14;
				}
				if (v6 > v15 + 1) {
					v6 = v15 + 1;
					d6 = d15;
				}
				if (v6 > v5 + 1) {
					v6 = v5 + 1;
					d6 = d5;
				}
				if (v6 > v16 + 1) {
					v6 = v16 + 1;
					d6 = d16;
				}
		}
		if (rc.onTheMap(l18)) {
				if (v18 > v28 + 1) {
					v18 = v28 + 1;
					d18 = d28;
				}
				if (v18 > v19 + 1) {
					v18 = v19 + 1;
					d18 = d19;
				}
				if (v18 > v27 + 1) {
					v18 = v27 + 1;
					d18 = d27;
				}
				if (v18 > v10 + 1) {
					v18 = v10 + 1;
					d18 = d10;
				}
		}
		if (rc.onTheMap(l26)) {
				if (v26 > v34 + 1) {
					v26 = v34 + 1;
					d26 = d34;
				}
				if (v26 > v25 + 1) {
					v26 = v25 + 1;
					d26 = d25;
				}
				if (v26 > v36 + 1) {
					v26 = v36 + 1;
					d26 = d36;
				}
				if (v26 > v27 + 1) {
					v26 = v27 + 1;
					d26 = d27;
				}
				if (v26 > v35 + 1) {
					v26 = v35 + 1;
					d26 = d35;
				}
				if (v26 > v16 + 1) {
					v26 = v16 + 1;
					d26 = d16;
				}
				if (v26 > v18 + 1) {
					v26 = v18 + 1;
					d26 = d18;
				}
		}
		if (rc.onTheMap(l54)) {
				if (v54 > v46 + 1) {
					v54 = v46 + 1;
					d54 = d46;
				}
				if (v54 > v55 + 1) {
					v54 = v55 + 1;
					d54 = d55;
				}
				if (v54 > v44 + 1) {
					v54 = v44 + 1;
					d54 = d44;
				}
				if (v54 > v45 + 1) {
					v54 = v45 + 1;
					d54 = d45;
				}
				if (v54 > v53 + 1) {
					v54 = v53 + 1;
					d54 = d53;
				}
				if (v54 > v64 + 1) {
					v54 = v64 + 1;
					d54 = d64;
				}
		}
		if (rc.onTheMap(l62)) {
				if (v62 > v52 + 1) {
					v62 = v52 + 1;
					d62 = d52;
				}
				if (v62 > v61 + 1) {
					v62 = v61 + 1;
					d62 = d61;
				}
				if (v62 > v53 + 1) {
					v62 = v53 + 1;
					d62 = d53;
				}
				if (v62 > v70 + 1) {
					v62 = v70 + 1;
					d62 = d70;
				}
				if (v62 > v54 + 1) {
					v62 = v54 + 1;
					d62 = d54;
				}
		}
		if (rc.onTheMap(l74)) {
				if (v74 > v66 + 1) {
					v74 = v66 + 1;
					d74 = d66;
				}
				if (v74 > v65 + 1) {
					v74 = v65 + 1;
					d74 = d65;
				}
				if (v74 > v75 + 1) {
					v74 = v75 + 1;
					d74 = d75;
				}
				if (v74 > v64 + 1) {
					v74 = v64 + 1;
					d74 = d64;
				}
		}
		if (rc.onTheMap(l78)) {
				if (v78 > v68 + 1) {
					v78 = v68 + 1;
					d78 = d68;
				}
				if (v78 > v69 + 1) {
					v78 = v69 + 1;
					d78 = d69;
				}
				if (v78 > v77 + 1) {
					v78 = v77 + 1;
					d78 = d77;
				}
				if (v78 > v70 + 1) {
					v78 = v70 + 1;
					d78 = d70;
				}
		}
		int dx = target.x - l40.x;
		int dy = target.y - l40.y;
		switch (dx) {
			case -4:
				switch (dy) {
					case -2:
						return d18;
					case -1:
						return d27;
					case 0:
						return d36;
					case 1:
						return d45;
					case 2:
						return d54;
				}
				break;
			case -3:
				switch (dy) {
					case -3:
						return d10;
					case -2:
						return d19;
					case -1:
						return d28;
					case 0:
						return d37;
					case 1:
						return d46;
					case 2:
						return d55;
					case 3:
						return d64;
				}
				break;
			case -2:
				switch (dy) {
					case -4:
						return d2;
					case -3:
						return d11;
					case -2:
						return d20;
					case -1:
						return d29;
					case 0:
						return d38;
					case 1:
						return d47;
					case 2:
						return d56;
					case 3:
						return d65;
					case 4:
						return d74;
				}
				break;
			case -1:
				switch (dy) {
					case -4:
						return d3;
					case -3:
						return d12;
					case -2:
						return d21;
					case -1:
						return d30;
					case 0:
						return d39;
					case 1:
						return d48;
					case 2:
						return d57;
					case 3:
						return d66;
					case 4:
						return d75;
				}
				break;
			case 0:
				switch (dy) {
					case -4:
						return d4;
					case -3:
						return d13;
					case -2:
						return d22;
					case -1:
						return d31;
					case 0:
						return d40;
					case 1:
						return d49;
					case 2:
						return d58;
					case 3:
						return d67;
					case 4:
						return d76;
				}
				break;
			case 1:
				switch (dy) {
					case -4:
						return d5;
					case -3:
						return d14;
					case -2:
						return d23;
					case -1:
						return d32;
					case 0:
						return d41;
					case 1:
						return d50;
					case 2:
						return d59;
					case 3:
						return d68;
					case 4:
						return d77;
				}
				break;
			case 2:
				switch (dy) {
					case -4:
						return d6;
					case -3:
						return d15;
					case -2:
						return d24;
					case -1:
						return d33;
					case 0:
						return d42;
					case 1:
						return d51;
					case 2:
						return d60;
					case 3:
						return d69;
					case 4:
						return d78;
				}
				break;
			case 3:
				switch (dy) {
					case -3:
						return d16;
					case -2:
						return d25;
					case -1:
						return d34;
					case 0:
						return d43;
					case 1:
						return d52;
					case 2:
						return d61;
					case 3:
						return d70;
				}
				break;
			case 4:
				switch (dy) {
					case -2:
						return d26;
					case -1:
						return d35;
					case 0:
						return d44;
					case 1:
						return d53;
					case 2:
						return d62;
				}
				break;
		}
		Direction ans = null;
		double bestEstimation = 0;
		double initialDist = Math.sqrt(l40.distanceSquaredTo(target));
		double dist4 = (initialDist - Math.sqrt(l4.distanceSquaredTo(target))) / v4;
		if (dist4 > bestEstimation) {
			bestEstimation = dist4;
			ans = d4;
		}
		double dist76 = (initialDist - Math.sqrt(l76.distanceSquaredTo(target))) / v76;
		if (dist76 > bestEstimation) {
			bestEstimation = dist76;
			ans = d76;
		}
		double dist3 = (initialDist - Math.sqrt(l3.distanceSquaredTo(target))) / v3;
		if (dist3 > bestEstimation) {
			bestEstimation = dist3;
			ans = d3;
		}
		double dist5 = (initialDist - Math.sqrt(l5.distanceSquaredTo(target))) / v5;
		if (dist5 > bestEstimation) {
			bestEstimation = dist5;
			ans = d5;
		}
		double dist75 = (initialDist - Math.sqrt(l75.distanceSquaredTo(target))) / v75;
		if (dist75 > bestEstimation) {
			bestEstimation = dist75;
			ans = d75;
		}
		double dist77 = (initialDist - Math.sqrt(l77.distanceSquaredTo(target))) / v77;
		if (dist77 > bestEstimation) {
			bestEstimation = dist77;
			ans = d77;
		}
		double dist10 = (initialDist - Math.sqrt(l10.distanceSquaredTo(target))) / v10;
		if (dist10 > bestEstimation) {
			bestEstimation = dist10;
			ans = d10;
		}
		double dist16 = (initialDist - Math.sqrt(l16.distanceSquaredTo(target))) / v16;
		if (dist16 > bestEstimation) {
			bestEstimation = dist16;
			ans = d16;
		}
		double dist64 = (initialDist - Math.sqrt(l64.distanceSquaredTo(target))) / v64;
		if (dist64 > bestEstimation) {
			bestEstimation = dist64;
			ans = d64;
		}
		double dist70 = (initialDist - Math.sqrt(l70.distanceSquaredTo(target))) / v70;
		if (dist70 > bestEstimation) {
			bestEstimation = dist70;
			ans = d70;
		}
		double dist2 = (initialDist - Math.sqrt(l2.distanceSquaredTo(target))) / v2;
		if (dist2 > bestEstimation) {
			bestEstimation = dist2;
			ans = d2;
		}
		double dist6 = (initialDist - Math.sqrt(l6.distanceSquaredTo(target))) / v6;
		if (dist6 > bestEstimation) {
			bestEstimation = dist6;
			ans = d6;
		}
		double dist18 = (initialDist - Math.sqrt(l18.distanceSquaredTo(target))) / v18;
		if (dist18 > bestEstimation) {
			bestEstimation = dist18;
			ans = d18;
		}
		double dist26 = (initialDist - Math.sqrt(l26.distanceSquaredTo(target))) / v26;
		if (dist26 > bestEstimation) {
			bestEstimation = dist26;
			ans = d26;
		}
		double dist54 = (initialDist - Math.sqrt(l54.distanceSquaredTo(target))) / v54;
		if (dist54 > bestEstimation) {
			bestEstimation = dist54;
			ans = d54;
		}
		double dist62 = (initialDist - Math.sqrt(l62.distanceSquaredTo(target))) / v62;
		if (dist62 > bestEstimation) {
			bestEstimation = dist62;
			ans = d62;
		}
		double dist74 = (initialDist - Math.sqrt(l74.distanceSquaredTo(target))) / v74;
		if (dist74 > bestEstimation) {
			bestEstimation = dist74;
			ans = d74;
		}
		double dist78 = (initialDist - Math.sqrt(l78.distanceSquaredTo(target))) / v78;
		if (dist78 > bestEstimation) {
			bestEstimation = dist78;
			ans = d78;
		}
		return ans;
	}
}