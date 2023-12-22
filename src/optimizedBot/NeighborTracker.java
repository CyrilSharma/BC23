package optimizedBot;
import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;

public class NeighborTracker {
    RobotController rc;
    static final int FE_MASK_WIDTH = 11;
    static final int FE_MASK_HEIGHT = 5;
    long friend_mask[] = { 0, 0 };
    long enemy_mask[] = { 0, 0 };
    public boolean hasLaunchersNear = false;
    public boolean hasCarriersNear = false;

    public NeighborTracker(RobotController rc) {
        this.rc = rc;
    }

    void updateNeighbors() throws GameActionException {
        int initial = Clock.getBytecodesLeft();
        load_friends();
        load_enemies();
        int end = Clock.getBytecodesLeft();
        System.out.println("Tracker Used: " + (initial - end));
        // System.out.println(rc.getLocation().hashCode() + " " + rc.getLocation().toString());
    }

    void load_friends() throws GameActionException {
        int tfriend_mask0 = 0;
        int tfriend_mask1 = 0;
        int tfriend_mask2 = 0;
        int tfriend_mask3 = 0;
        int tfriend_mask4 = 0;
        int tfriend_mask5 = 0;
        RobotInfo[] friends = rc.senseNearbyRobots(-1, rc.getTeam());
        for (int j = friends.length; j-- > 0; ) {
            RobotInfo r = friends[j];
            switch (r.type) {
                case LAUNCHER:
                switch (r.location.hashCode()) {
                    case 32768: case 3571715: case 2981892: case 2392069: case 1802246: case 1212423: 
                    case 622600: case 32777: case 3571724: case 2981901: case 2392078: case 1802255: 
                    case 1212432: case 622609: case 32786: case 3571733: case 2981910: case 2392087: 
                    case 1802264: case 1212441: case 622618: case 32795: case 3571742: case 2981919: 
                    case 2392096: case 1802273: case 1212450: case 622627: case 32804: case 3571751: 
                    case 2981928: case 2392105: case 1802282: case 1212459: case 622636: case 32813: 
                    case 3571760: case 2981937: case 2392114: case 1802291: case 1212468: case 622645: 
                    case 32822: case 3571769: case 2981946: case 2392123: 
                        tfriend_mask0 += 1; continue;
                    case 98304: case 3637251: case 3047428: case 2457605: case 1867782: case 1277959: 
                    case 688136: case 98313: case 3637260: case 3047437: case 2457614: case 1867791: 
                    case 1277968: case 688145: case 98322: case 3637269: case 3047446: case 2457623: 
                    case 1867800: case 1277977: case 688154: case 98331: case 3637278: case 3047455: 
                    case 2457632: case 1867809: case 1277986: case 688163: case 98340: case 3637287: 
                    case 3047464: case 2457641: case 1867818: case 1277995: case 688172: case 98349: 
                    case 3637296: case 3047473: case 2457650: case 1867827: case 1278004: case 688181: 
                    case 98358: case 3637305: case 3047482: case 2457659: 
                        tfriend_mask0 += 2; continue;
                    case 163840: case 3702787: case 3112964: case 2523141: case 1933318: case 1343495: 
                    case 753672: case 163849: case 3702796: case 3112973: case 2523150: case 1933327: 
                    case 1343504: case 753681: case 163858: case 3702805: case 3112982: case 2523159: 
                    case 1933336: case 1343513: case 753690: case 163867: case 3702814: case 3112991: 
                    case 2523168: case 1933345: case 1343522: case 753699: case 163876: case 3702823: 
                    case 3113000: case 2523177: case 1933354: case 1343531: case 753708: case 163885: 
                    case 3702832: case 3113009: case 2523186: case 1933363: case 1343540: case 753717: 
                    case 163894: case 3702841: case 3113018: case 2523195: 
                        tfriend_mask0 += 4; continue;
                    case 229376: case 3768323: case 3178500: case 2588677: case 1998854: case 1409031: 
                    case 819208: case 229385: case 3768332: case 3178509: case 2588686: case 1998863: 
                    case 1409040: case 819217: case 229394: case 3768341: case 3178518: case 2588695: 
                    case 1998872: case 1409049: case 819226: case 229403: case 3768350: case 3178527: 
                    case 2588704: case 1998881: case 1409058: case 819235: case 229412: case 3768359: 
                    case 3178536: case 2588713: case 1998890: case 1409067: case 819244: case 229421: 
                    case 3768368: case 3178545: case 2588722: case 1998899: case 1409076: case 819253: 
                    case 229430: case 3768377: case 3178554: case 2588731: 
                        tfriend_mask0 += 8; continue;
                    case 294912: case 3833859: case 3244036: case 2654213: case 2064390: case 1474567: 
                    case 884744: case 294921: case 3833868: case 3244045: case 2654222: case 2064399: 
                    case 1474576: case 884753: case 294930: case 3833877: case 3244054: case 2654231: 
                    case 2064408: case 1474585: case 884762: case 294939: case 3833886: case 3244063: 
                    case 2654240: case 2064417: case 1474594: case 884771: case 294948: case 3833895: 
                    case 3244072: case 2654249: case 2064426: case 1474603: case 884780: case 294957: 
                    case 3833904: case 3244081: case 2654258: case 2064435: case 1474612: case 884789: 
                    case 294966: case 3833913: case 3244090: case 2654267: 
                        tfriend_mask0 += 16; continue;
                    case 360448: case 3899395: case 3309572: case 2719749: case 2129926: case 1540103: 
                    case 950280: case 360457: case 3899404: case 3309581: case 2719758: case 2129935: 
                    case 1540112: case 950289: case 360466: case 3899413: case 3309590: case 2719767: 
                    case 2129944: case 1540121: case 950298: case 360475: case 3899422: case 3309599: 
                    case 2719776: case 2129953: case 1540130: case 950307: case 360484: case 3899431: 
                    case 3309608: case 2719785: case 2129962: case 1540139: case 950316: case 360493: 
                    case 3899440: case 3309617: case 2719794: case 2129971: case 1540148: case 950325: 
                    case 360502: case 3899449: case 3309626: case 2719803: 
                        tfriend_mask0 += 32; continue;
                    case 425984: case 3375108: case 2785285: case 2195462: case 1605639: case 1015816: 
                    case 425993: case 3375117: case 2785294: case 2195471: case 1605648: case 1015825: 
                    case 426002: case 3375126: case 2785303: case 2195480: case 1605657: case 1015834: 
                    case 426011: case 3375135: case 2785312: case 2195489: case 1605666: case 1015843: 
                    case 426020: case 3375144: case 2785321: case 2195498: case 1605675: case 1015852: 
                    case 426029: case 3375153: case 2785330: case 2195507: case 1605684: case 1015861: 
                    case 426038: case 3375162: case 2785339: 
                        tfriend_mask0 += 64; continue;
                    case 491520: case 3440644: case 2850821: case 2260998: case 1671175: case 1081352: 
                    case 491529: case 3440653: case 2850830: case 2261007: case 1671184: case 1081361: 
                    case 491538: case 3440662: case 2850839: case 2261016: case 1671193: case 1081370: 
                    case 491547: case 3440671: case 2850848: case 2261025: case 1671202: case 1081379: 
                    case 491556: case 3440680: case 2850857: case 2261034: case 1671211: case 1081388: 
                    case 491565: case 3440689: case 2850866: case 2261043: case 1671220: case 1081397: 
                    case 491574: case 3440698: case 2850875: 
                        tfriend_mask0 += 128; continue;
                    case 557056: case 3506180: case 2916357: case 2326534: case 1736711: case 1146888: 
                    case 557065: case 3506189: case 2916366: case 2326543: case 1736720: case 1146897: 
                    case 557074: case 3506198: case 2916375: case 2326552: case 1736729: case 1146906: 
                    case 557083: case 3506207: case 2916384: case 2326561: case 1736738: case 1146915: 
                    case 557092: case 3506216: case 2916393: case 2326570: case 1736747: case 1146924: 
                    case 557101: case 3506225: case 2916402: case 2326579: case 1736756: case 1146933: 
                    case 557110: case 3506234: case 2916411: 
                        tfriend_mask0 += 256; continue;
                    case 622592: case 32769: case 3571716: case 2981893: case 2392070: case 1802247: 
                    case 1212424: case 622601: case 32778: case 3571725: case 2981902: case 2392079: 
                    case 1802256: case 1212433: case 622610: case 32787: case 3571734: case 2981911: 
                    case 2392088: case 1802265: case 1212442: case 622619: case 32796: case 3571743: 
                    case 2981920: case 2392097: case 1802274: case 1212451: case 622628: case 32805: 
                    case 3571752: case 2981929: case 2392106: case 1802283: case 1212460: case 622637: 
                    case 32814: case 3571761: case 2981938: case 2392115: case 1802292: case 1212469: 
                    case 622646: case 32823: case 3571770: case 2981947: 
                        tfriend_mask0 += 512; continue;
                    case 688128: case 98305: case 3637252: case 3047429: case 2457606: case 1867783: 
                    case 1277960: case 688137: case 98314: case 3637261: case 3047438: case 2457615: 
                    case 1867792: case 1277969: case 688146: case 98323: case 3637270: case 3047447: 
                    case 2457624: case 1867801: case 1277978: case 688155: case 98332: case 3637279: 
                    case 3047456: case 2457633: case 1867810: case 1277987: case 688164: case 98341: 
                    case 3637288: case 3047465: case 2457642: case 1867819: case 1277996: case 688173: 
                    case 98350: case 3637297: case 3047474: case 2457651: case 1867828: case 1278005: 
                    case 688182: case 98359: case 3637306: case 3047483: 
                        tfriend_mask0 += 1024; continue;
                    case 753664: case 163841: case 3702788: case 3112965: case 2523142: case 1933319: 
                    case 1343496: case 753673: case 163850: case 3702797: case 3112974: case 2523151: 
                    case 1933328: case 1343505: case 753682: case 163859: case 3702806: case 3112983: 
                    case 2523160: case 1933337: case 1343514: case 753691: case 163868: case 3702815: 
                    case 3112992: case 2523169: case 1933346: case 1343523: case 753700: case 163877: 
                    case 3702824: case 3113001: case 2523178: case 1933355: case 1343532: case 753709: 
                    case 163886: case 3702833: case 3113010: case 2523187: case 1933364: case 1343541: 
                    case 753718: case 163895: case 3702842: case 3113019: 
                        tfriend_mask0 += 2048; continue;
                    case 819200: case 229377: case 3768324: case 3178501: case 2588678: case 1998855: 
                    case 1409032: case 819209: case 229386: case 3768333: case 3178510: case 2588687: 
                    case 1998864: case 1409041: case 819218: case 229395: case 3768342: case 3178519: 
                    case 2588696: case 1998873: case 1409050: case 819227: case 229404: case 3768351: 
                    case 3178528: case 2588705: case 1998882: case 1409059: case 819236: case 229413: 
                    case 3768360: case 3178537: case 2588714: case 1998891: case 1409068: case 819245: 
                    case 229422: case 3768369: case 3178546: case 2588723: case 1998900: case 1409077: 
                    case 819254: case 229431: case 3768378: case 3178555: 
                        tfriend_mask0 += 4096; continue;
                    case 884736: case 294913: case 3833860: case 3244037: case 2654214: case 2064391: 
                    case 1474568: case 884745: case 294922: case 3833869: case 3244046: case 2654223: 
                    case 2064400: case 1474577: case 884754: case 294931: case 3833878: case 3244055: 
                    case 2654232: case 2064409: case 1474586: case 884763: case 294940: case 3833887: 
                    case 3244064: case 2654241: case 2064418: case 1474595: case 884772: case 294949: 
                    case 3833896: case 3244073: case 2654250: case 2064427: case 1474604: case 884781: 
                    case 294958: case 3833905: case 3244082: case 2654259: case 2064436: case 1474613: 
                    case 884790: case 294967: case 3833914: case 3244091: 
                        tfriend_mask0 += 8192; continue;
                    case 950272: case 360449: case 3899396: case 3309573: case 2719750: case 2129927: 
                    case 1540104: case 950281: case 360458: case 3899405: case 3309582: case 2719759: 
                    case 2129936: case 1540113: case 950290: case 360467: case 3899414: case 3309591: 
                    case 2719768: case 2129945: case 1540122: case 950299: case 360476: case 3899423: 
                    case 3309600: case 2719777: case 2129954: case 1540131: case 950308: case 360485: 
                    case 3899432: case 3309609: case 2719786: case 2129963: case 1540140: case 950317: 
                    case 360494: case 3899441: case 3309618: case 2719795: case 2129972: case 1540149: 
                    case 950326: case 360503: case 3899450: case 3309627: 
                        tfriend_mask0 += 16384; continue;
                    case 1015808: case 425985: case 3375109: case 2785286: case 2195463: case 1605640: 
                    case 1015817: case 425994: case 3375118: case 2785295: case 2195472: case 1605649: 
                    case 1015826: case 426003: case 3375127: case 2785304: case 2195481: case 1605658: 
                    case 1015835: case 426012: case 3375136: case 2785313: case 2195490: case 1605667: 
                    case 1015844: case 426021: case 3375145: case 2785322: case 2195499: case 1605676: 
                    case 1015853: case 426030: case 3375154: case 2785331: case 2195508: case 1605685: 
                    case 1015862: case 426039: case 3375163: 
                        tfriend_mask0 += 16384;
                        tfriend_mask0 += 16384;
                        continue;
                    case 1081344: case 491521: case 3440645: case 2850822: case 2260999: case 1671176: 
                    case 1081353: case 491530: case 3440654: case 2850831: case 2261008: case 1671185: 
                    case 1081362: case 491539: case 3440663: case 2850840: case 2261017: case 1671194: 
                    case 1081371: case 491548: case 3440672: case 2850849: case 2261026: case 1671203: 
                    case 1081380: case 491557: case 3440681: case 2850858: case 2261035: case 1671212: 
                    case 1081389: case 491566: case 3440690: case 2850867: case 2261044: case 1671221: 
                    case 1081398: case 491575: case 3440699: 
                        tfriend_mask1 += 1; continue;
                    case 1146880: case 557057: case 3506181: case 2916358: case 2326535: case 1736712: 
                    case 1146889: case 557066: case 3506190: case 2916367: case 2326544: case 1736721: 
                    case 1146898: case 557075: case 3506199: case 2916376: case 2326553: case 1736730: 
                    case 1146907: case 557084: case 3506208: case 2916385: case 2326562: case 1736739: 
                    case 1146916: case 557093: case 3506217: case 2916394: case 2326571: case 1736748: 
                    case 1146925: case 557102: case 3506226: case 2916403: case 2326580: case 1736757: 
                    case 1146934: case 557111: case 3506235: 
                        tfriend_mask1 += 2; continue;
                    case 1212416: case 622593: case 32770: case 3571717: case 2981894: case 2392071: 
                    case 1802248: case 1212425: case 622602: case 32779: case 3571726: case 2981903: 
                    case 2392080: case 1802257: case 1212434: case 622611: case 32788: case 3571735: 
                    case 2981912: case 2392089: case 1802266: case 1212443: case 622620: case 32797: 
                    case 3571744: case 2981921: case 2392098: case 1802275: case 1212452: case 622629: 
                    case 32806: case 3571753: case 2981930: case 2392107: case 1802284: case 1212461: 
                    case 622638: case 32815: case 3571762: case 2981939: case 2392116: case 1802293: 
                    case 1212470: case 622647: case 32824: case 3571771: 
                        tfriend_mask1 += 4; continue;
                    case 1277952: case 688129: case 98306: case 3637253: case 3047430: case 2457607: 
                    case 1867784: case 1277961: case 688138: case 98315: case 3637262: case 3047439: 
                    case 2457616: case 1867793: case 1277970: case 688147: case 98324: case 3637271: 
                    case 3047448: case 2457625: case 1867802: case 1277979: case 688156: case 98333: 
                    case 3637280: case 3047457: case 2457634: case 1867811: case 1277988: case 688165: 
                    case 98342: case 3637289: case 3047466: case 2457643: case 1867820: case 1277997: 
                    case 688174: case 98351: case 3637298: case 3047475: case 2457652: case 1867829: 
                    case 1278006: case 688183: case 98360: case 3637307: 
                        tfriend_mask1 += 8; continue;
                    case 1343488: case 753665: case 163842: case 3702789: case 3112966: case 2523143: 
                    case 1933320: case 1343497: case 753674: case 163851: case 3702798: case 3112975: 
                    case 2523152: case 1933329: case 1343506: case 753683: case 163860: case 3702807: 
                    case 3112984: case 2523161: case 1933338: case 1343515: case 753692: case 163869: 
                    case 3702816: case 3112993: case 2523170: case 1933347: case 1343524: case 753701: 
                    case 163878: case 3702825: case 3113002: case 2523179: case 1933356: case 1343533: 
                    case 753710: case 163887: case 3702834: case 3113011: case 2523188: case 1933365: 
                    case 1343542: case 753719: case 163896: case 3702843: 
                        tfriend_mask1 += 16; continue;
                    case 1409024: case 819201: case 229378: case 3768325: case 3178502: case 2588679: 
                    case 1998856: case 1409033: case 819210: case 229387: case 3768334: case 3178511: 
                    case 2588688: case 1998865: case 1409042: case 819219: case 229396: case 3768343: 
                    case 3178520: case 2588697: case 1998874: case 1409051: case 819228: case 229405: 
                    case 3768352: case 3178529: case 2588706: case 1998883: case 1409060: case 819237: 
                    case 229414: case 3768361: case 3178538: case 2588715: case 1998892: case 1409069: 
                    case 819246: case 229423: case 3768370: case 3178547: case 2588724: case 1998901: 
                    case 1409078: case 819255: case 229432: case 3768379: 
                        tfriend_mask1 += 32; continue;
                    case 1474560: case 884737: case 294914: case 3833861: case 3244038: case 2654215: 
                    case 2064392: case 1474569: case 884746: case 294923: case 3833870: case 3244047: 
                    case 2654224: case 2064401: case 1474578: case 884755: case 294932: case 3833879: 
                    case 3244056: case 2654233: case 2064410: case 1474587: case 884764: case 294941: 
                    case 3833888: case 3244065: case 2654242: case 2064419: case 1474596: case 884773: 
                    case 294950: case 3833897: case 3244074: case 2654251: case 2064428: case 1474605: 
                    case 884782: case 294959: case 3833906: case 3244083: case 2654260: case 2064437: 
                    case 1474614: case 884791: case 294968: case 3833915: 
                        tfriend_mask1 += 64; continue;
                    case 1540096: case 950273: case 360450: case 3899397: case 3309574: case 2719751: 
                    case 2129928: case 1540105: case 950282: case 360459: case 3899406: case 3309583: 
                    case 2719760: case 2129937: case 1540114: case 950291: case 360468: case 3899415: 
                    case 3309592: case 2719769: case 2129946: case 1540123: case 950300: case 360477: 
                    case 3899424: case 3309601: case 2719778: case 2129955: case 1540132: case 950309: 
                    case 360486: case 3899433: case 3309610: case 2719787: case 2129964: case 1540141: 
                    case 950318: case 360495: case 3899442: case 3309619: case 2719796: case 2129973: 
                    case 1540150: case 950327: case 360504: case 3899451: 
                        tfriend_mask1 += 128; continue;
                    case 1605632: case 1015809: case 425986: case 3375110: case 2785287: case 2195464: 
                    case 1605641: case 1015818: case 425995: case 3375119: case 2785296: case 2195473: 
                    case 1605650: case 1015827: case 426004: case 3375128: case 2785305: case 2195482: 
                    case 1605659: case 1015836: case 426013: case 3375137: case 2785314: case 2195491: 
                    case 1605668: case 1015845: case 426022: case 3375146: case 2785323: case 2195500: 
                    case 1605677: case 1015854: case 426031: case 3375155: case 2785332: case 2195509: 
                    case 1605686: case 1015863: case 426040: 
                        tfriend_mask1 += 256; continue;
                    case 1671168: case 1081345: case 491522: case 3440646: case 2850823: case 2261000: 
                    case 1671177: case 1081354: case 491531: case 3440655: case 2850832: case 2261009: 
                    case 1671186: case 1081363: case 491540: case 3440664: case 2850841: case 2261018: 
                    case 1671195: case 1081372: case 491549: case 3440673: case 2850850: case 2261027: 
                    case 1671204: case 1081381: case 491558: case 3440682: case 2850859: case 2261036: 
                    case 1671213: case 1081390: case 491567: case 3440691: case 2850868: case 2261045: 
                    case 1671222: case 1081399: case 491576: 
                        tfriend_mask1 += 512; continue;
                    case 1736704: case 1146881: case 557058: case 3506182: case 2916359: case 2326536: 
                    case 1736713: case 1146890: case 557067: case 3506191: case 2916368: case 2326545: 
                    case 1736722: case 1146899: case 557076: case 3506200: case 2916377: case 2326554: 
                    case 1736731: case 1146908: case 557085: case 3506209: case 2916386: case 2326563: 
                    case 1736740: case 1146917: case 557094: case 3506218: case 2916395: case 2326572: 
                    case 1736749: case 1146926: case 557103: case 3506227: case 2916404: case 2326581: 
                    case 1736758: case 1146935: case 557112: 
                        tfriend_mask1 += 1024; continue;
                    case 1802240: case 1212417: case 622594: case 32771: case 3571718: case 2981895: 
                    case 2392072: case 1802249: case 1212426: case 622603: case 32780: case 3571727: 
                    case 2981904: case 2392081: case 1802258: case 1212435: case 622612: case 32789: 
                    case 3571736: case 2981913: case 2392090: case 1802267: case 1212444: case 622621: 
                    case 32798: case 3571745: case 2981922: case 2392099: case 1802276: case 1212453: 
                    case 622630: case 32807: case 3571754: case 2981931: case 2392108: case 1802285: 
                    case 1212462: case 622639: case 32816: case 3571763: case 2981940: case 2392117: 
                    case 1802294: case 1212471: case 622648: case 32825: 
                        tfriend_mask1 += 2048; continue;
                    case 1867776: case 1277953: case 688130: case 98307: case 3637254: case 3047431: 
                    case 2457608: case 1867785: case 1277962: case 688139: case 98316: case 3637263: 
                    case 3047440: case 2457617: case 1867794: case 1277971: case 688148: case 98325: 
                    case 3637272: case 3047449: case 2457626: case 1867803: case 1277980: case 688157: 
                    case 98334: case 3637281: case 3047458: case 2457635: case 1867812: case 1277989: 
                    case 688166: case 98343: case 3637290: case 3047467: case 2457644: case 1867821: 
                    case 1277998: case 688175: case 98352: case 3637299: case 3047476: case 2457653: 
                    case 1867830: case 1278007: case 688184: case 98361: 
                        tfriend_mask1 += 4096; continue;
                    case 1933312: case 1343489: case 753666: case 163843: case 3702790: case 3112967: 
                    case 2523144: case 1933321: case 1343498: case 753675: case 163852: case 3702799: 
                    case 3112976: case 2523153: case 1933330: case 1343507: case 753684: case 163861: 
                    case 3702808: case 3112985: case 2523162: case 1933339: case 1343516: case 753693: 
                    case 163870: case 3702817: case 3112994: case 2523171: case 1933348: case 1343525: 
                    case 753702: case 163879: case 3702826: case 3113003: case 2523180: case 1933357: 
                    case 1343534: case 753711: case 163888: case 3702835: case 3113012: case 2523189: 
                    case 1933366: case 1343543: case 753720: case 163897: 
                        tfriend_mask1 += 8192; continue;
                    case 1998848: case 1409025: case 819202: case 229379: case 3768326: case 3178503: 
                    case 2588680: case 1998857: case 1409034: case 819211: case 229388: case 3768335: 
                    case 3178512: case 2588689: case 1998866: case 1409043: case 819220: case 229397: 
                    case 3768344: case 3178521: case 2588698: case 1998875: case 1409052: case 819229: 
                    case 229406: case 3768353: case 3178530: case 2588707: case 1998884: case 1409061: 
                    case 819238: case 229415: case 3768362: case 3178539: case 2588716: case 1998893: 
                    case 1409070: case 819247: case 229424: case 3768371: case 3178548: case 2588725: 
                    case 1998902: case 1409079: case 819256: case 229433: 
                        tfriend_mask1 += 16384; continue;
                    case 2064384: case 1474561: case 884738: case 294915: case 3833862: case 3244039: 
                    case 2654216: case 2064393: case 1474570: case 884747: case 294924: case 3833871: 
                    case 3244048: case 2654225: case 2064402: case 1474579: case 884756: case 294933: 
                    case 3833880: case 3244057: case 2654234: case 2064411: case 1474588: case 884765: 
                    case 294942: case 3833889: case 3244066: case 2654243: case 2064420: case 1474597: 
                    case 884774: case 294951: case 3833898: case 3244075: case 2654252: case 2064429: 
                    case 1474606: case 884783: case 294960: case 3833907: case 3244084: case 2654261: 
                    case 2064438: case 1474615: case 884792: case 294969: 
                        tfriend_mask1 += 16384;
                        tfriend_mask1 += 16384;
                        continue;
                    case 2129920: case 1540097: case 950274: case 360451: case 3899398: case 3309575: 
                    case 2719752: case 2129929: case 1540106: case 950283: case 360460: case 3899407: 
                    case 3309584: case 2719761: case 2129938: case 1540115: case 950292: case 360469: 
                    case 3899416: case 3309593: case 2719770: case 2129947: case 1540124: case 950301: 
                    case 360478: case 3899425: case 3309602: case 2719779: case 2129956: case 1540133: 
                    case 950310: case 360487: case 3899434: case 3309611: case 2719788: case 2129965: 
                    case 1540142: case 950319: case 360496: case 3899443: case 3309620: case 2719797: 
                    case 2129974: case 1540151: case 950328: case 360505: 
                        tfriend_mask2 += 1; continue;
                    case 2195456: case 1605633: case 1015810: case 425987: case 3375111: case 2785288: 
                    case 2195465: case 1605642: case 1015819: case 425996: case 3375120: case 2785297: 
                    case 2195474: case 1605651: case 1015828: case 426005: case 3375129: case 2785306: 
                    case 2195483: case 1605660: case 1015837: case 426014: case 3375138: case 2785315: 
                    case 2195492: case 1605669: case 1015846: case 426023: case 3375147: case 2785324: 
                    case 2195501: case 1605678: case 1015855: case 426032: case 3375156: case 2785333: 
                    case 2195510: case 1605687: case 1015864: case 426041: 
                        tfriend_mask2 += 2; continue;
                    case 2260992: case 1671169: case 1081346: case 491523: case 3440647: case 2850824: 
                    case 2261001: case 1671178: case 1081355: case 491532: case 3440656: case 2850833: 
                    case 2261010: case 1671187: case 1081364: case 491541: case 3440665: case 2850842: 
                    case 2261019: case 1671196: case 1081373: case 491550: case 3440674: case 2850851: 
                    case 2261028: case 1671205: case 1081382: case 491559: case 3440683: case 2850860: 
                    case 2261037: case 1671214: case 1081391: case 491568: case 3440692: case 2850869: 
                    case 2261046: case 1671223: case 1081400: case 491577: 
                        tfriend_mask2 += 4; continue;
                    case 2326528: case 1736705: case 1146882: case 557059: case 3506183: case 2916360: 
                    case 2326537: case 1736714: case 1146891: case 557068: case 3506192: case 2916369: 
                    case 2326546: case 1736723: case 1146900: case 557077: case 3506201: case 2916378: 
                    case 2326555: case 1736732: case 1146909: case 557086: case 3506210: case 2916387: 
                    case 2326564: case 1736741: case 1146918: case 557095: case 3506219: case 2916396: 
                    case 2326573: case 1736750: case 1146927: case 557104: case 3506228: case 2916405: 
                    case 2326582: case 1736759: case 1146936: case 557113: 
                        tfriend_mask2 += 8; continue;
                    case 2392064: case 1802241: case 1212418: case 622595: case 32772: case 3571719: 
                    case 2981896: case 2392073: case 1802250: case 1212427: case 622604: case 32781: 
                    case 3571728: case 2981905: case 2392082: case 1802259: case 1212436: case 622613: 
                    case 32790: case 3571737: case 2981914: case 2392091: case 1802268: case 1212445: 
                    case 622622: case 32799: case 3571746: case 2981923: case 2392100: case 1802277: 
                    case 1212454: case 622631: case 32808: case 3571755: case 2981932: case 2392109: 
                    case 1802286: case 1212463: case 622640: case 32817: case 3571764: case 2981941: 
                    case 2392118: case 1802295: case 1212472: case 622649: case 32826: 
                        tfriend_mask2 += 16; continue;
                    case 2457600: case 1867777: case 1277954: case 688131: case 98308: case 3637255: 
                    case 3047432: case 2457609: case 1867786: case 1277963: case 688140: case 98317: 
                    case 3637264: case 3047441: case 2457618: case 1867795: case 1277972: case 688149: 
                    case 98326: case 3637273: case 3047450: case 2457627: case 1867804: case 1277981: 
                    case 688158: case 98335: case 3637282: case 3047459: case 2457636: case 1867813: 
                    case 1277990: case 688167: case 98344: case 3637291: case 3047468: case 2457645: 
                    case 1867822: case 1277999: case 688176: case 98353: case 3637300: case 3047477: 
                    case 2457654: case 1867831: case 1278008: case 688185: case 98362: 
                        tfriend_mask2 += 32; continue;
                    case 2523136: case 1933313: case 1343490: case 753667: case 163844: case 3702791: 
                    case 3112968: case 2523145: case 1933322: case 1343499: case 753676: case 163853: 
                    case 3702800: case 3112977: case 2523154: case 1933331: case 1343508: case 753685: 
                    case 163862: case 3702809: case 3112986: case 2523163: case 1933340: case 1343517: 
                    case 753694: case 163871: case 3702818: case 3112995: case 2523172: case 1933349: 
                    case 1343526: case 753703: case 163880: case 3702827: case 3113004: case 2523181: 
                    case 1933358: case 1343535: case 753712: case 163889: case 3702836: case 3113013: 
                    case 2523190: case 1933367: case 1343544: case 753721: case 163898: 
                        tfriend_mask2 += 64; continue;
                    case 2588672: case 1998849: case 1409026: case 819203: case 229380: case 3768327: 
                    case 3178504: case 2588681: case 1998858: case 1409035: case 819212: case 229389: 
                    case 3768336: case 3178513: case 2588690: case 1998867: case 1409044: case 819221: 
                    case 229398: case 3768345: case 3178522: case 2588699: case 1998876: case 1409053: 
                    case 819230: case 229407: case 3768354: case 3178531: case 2588708: case 1998885: 
                    case 1409062: case 819239: case 229416: case 3768363: case 3178540: case 2588717: 
                    case 1998894: case 1409071: case 819248: case 229425: case 3768372: case 3178549: 
                    case 2588726: case 1998903: case 1409080: case 819257: case 229434: 
                        tfriend_mask2 += 128; continue;
                    case 2654208: case 2064385: case 1474562: case 884739: case 294916: case 3833863: 
                    case 3244040: case 2654217: case 2064394: case 1474571: case 884748: case 294925: 
                    case 3833872: case 3244049: case 2654226: case 2064403: case 1474580: case 884757: 
                    case 294934: case 3833881: case 3244058: case 2654235: case 2064412: case 1474589: 
                    case 884766: case 294943: case 3833890: case 3244067: case 2654244: case 2064421: 
                    case 1474598: case 884775: case 294952: case 3833899: case 3244076: case 2654253: 
                    case 2064430: case 1474607: case 884784: case 294961: case 3833908: case 3244085: 
                    case 2654262: case 2064439: case 1474616: case 884793: case 294970: 
                        tfriend_mask2 += 256; continue;
                    case 2719744: case 2129921: case 1540098: case 950275: case 360452: case 3899399: 
                    case 3309576: case 2719753: case 2129930: case 1540107: case 950284: case 360461: 
                    case 3899408: case 3309585: case 2719762: case 2129939: case 1540116: case 950293: 
                    case 360470: case 3899417: case 3309594: case 2719771: case 2129948: case 1540125: 
                    case 950302: case 360479: case 3899426: case 3309603: case 2719780: case 2129957: 
                    case 1540134: case 950311: case 360488: case 3899435: case 3309612: case 2719789: 
                    case 2129966: case 1540143: case 950320: case 360497: case 3899444: case 3309621: 
                    case 2719798: case 2129975: case 1540152: case 950329: case 360506: 
                        tfriend_mask2 += 512; continue;
                    case 2785280: case 2195457: case 1605634: case 1015811: case 425988: case 3375112: 
                    case 2785289: case 2195466: case 1605643: case 1015820: case 425997: case 3375121: 
                    case 2785298: case 2195475: case 1605652: case 1015829: case 426006: case 3375130: 
                    case 2785307: case 2195484: case 1605661: case 1015838: case 426015: case 3375139: 
                    case 2785316: case 2195493: case 1605670: case 1015847: case 426024: case 3375148: 
                    case 2785325: case 2195502: case 1605679: case 1015856: case 426033: case 3375157: 
                    case 2785334: case 2195511: case 1605688: case 1015865: case 426042: 
                        tfriend_mask2 += 1024; continue;
                    case 2850816: case 2260993: case 1671170: case 1081347: case 491524: case 3440648: 
                    case 2850825: case 2261002: case 1671179: case 1081356: case 491533: case 3440657: 
                    case 2850834: case 2261011: case 1671188: case 1081365: case 491542: case 3440666: 
                    case 2850843: case 2261020: case 1671197: case 1081374: case 491551: case 3440675: 
                    case 2850852: case 2261029: case 1671206: case 1081383: case 491560: case 3440684: 
                    case 2850861: case 2261038: case 1671215: case 1081392: case 491569: case 3440693: 
                    case 2850870: case 2261047: case 1671224: case 1081401: case 491578: 
                        tfriend_mask2 += 2048; continue;
                    case 2916352: case 2326529: case 1736706: case 1146883: case 557060: case 3506184: 
                    case 2916361: case 2326538: case 1736715: case 1146892: case 557069: case 3506193: 
                    case 2916370: case 2326547: case 1736724: case 1146901: case 557078: case 3506202: 
                    case 2916379: case 2326556: case 1736733: case 1146910: case 557087: case 3506211: 
                    case 2916388: case 2326565: case 1736742: case 1146919: case 557096: case 3506220: 
                    case 2916397: case 2326574: case 1736751: case 1146928: case 557105: case 3506229: 
                    case 2916406: case 2326583: case 1736760: case 1146937: case 557114: 
                        tfriend_mask2 += 4096; continue;
                    case 2981888: case 2392065: case 1802242: case 1212419: case 622596: case 32773: 
                    case 3571720: case 2981897: case 2392074: case 1802251: case 1212428: case 622605: 
                    case 32782: case 3571729: case 2981906: case 2392083: case 1802260: case 1212437: 
                    case 622614: case 32791: case 3571738: case 2981915: case 2392092: case 1802269: 
                    case 1212446: case 622623: case 32800: case 3571747: case 2981924: case 2392101: 
                    case 1802278: case 1212455: case 622632: case 32809: case 3571756: case 2981933: 
                    case 2392110: case 1802287: case 1212464: case 622641: case 32818: case 3571765: 
                    case 2981942: case 2392119: case 1802296: case 1212473: case 622650: case 32827: 
                        tfriend_mask2 += 8192; continue;
                    case 3047424: case 2457601: case 1867778: case 1277955: case 688132: case 98309: 
                    case 3637256: case 3047433: case 2457610: case 1867787: case 1277964: case 688141: 
                    case 98318: case 3637265: case 3047442: case 2457619: case 1867796: case 1277973: 
                    case 688150: case 98327: case 3637274: case 3047451: case 2457628: case 1867805: 
                    case 1277982: case 688159: case 98336: case 3637283: case 3047460: case 2457637: 
                    case 1867814: case 1277991: case 688168: case 98345: case 3637292: case 3047469: 
                    case 2457646: case 1867823: case 1278000: case 688177: case 98354: case 3637301: 
                    case 3047478: case 2457655: case 1867832: case 1278009: case 688186: case 98363: 
                        tfriend_mask2 += 16384; continue;
                    case 3112960: case 2523137: case 1933314: case 1343491: case 753668: case 163845: 
                    case 3702792: case 3112969: case 2523146: case 1933323: case 1343500: case 753677: 
                    case 163854: case 3702801: case 3112978: case 2523155: case 1933332: case 1343509: 
                    case 753686: case 163863: case 3702810: case 3112987: case 2523164: case 1933341: 
                    case 1343518: case 753695: case 163872: case 3702819: case 3112996: case 2523173: 
                    case 1933350: case 1343527: case 753704: case 163881: case 3702828: case 3113005: 
                    case 2523182: case 1933359: case 1343536: case 753713: case 163890: case 3702837: 
                    case 3113014: case 2523191: case 1933368: case 1343545: case 753722: case 163899: 
                        tfriend_mask2 += 16384;
                        tfriend_mask2 += 16384;
                        continue;
                    case 3178496: case 2588673: case 1998850: case 1409027: case 819204: case 229381: 
                    case 3768328: case 3178505: case 2588682: case 1998859: case 1409036: case 819213: 
                    case 229390: case 3768337: case 3178514: case 2588691: case 1998868: case 1409045: 
                    case 819222: case 229399: case 3768346: case 3178523: case 2588700: case 1998877: 
                    case 1409054: case 819231: case 229408: case 3768355: case 3178532: case 2588709: 
                    case 1998886: case 1409063: case 819240: case 229417: case 3768364: case 3178541: 
                    case 2588718: case 1998895: case 1409072: case 819249: case 229426: case 3768373: 
                    case 3178550: case 2588727: case 1998904: case 1409081: case 819258: case 229435: 
                        tfriend_mask3 += 1; continue;
                    case 3244032: case 2654209: case 2064386: case 1474563: case 884740: case 294917: 
                    case 3833864: case 3244041: case 2654218: case 2064395: case 1474572: case 884749: 
                    case 294926: case 3833873: case 3244050: case 2654227: case 2064404: case 1474581: 
                    case 884758: case 294935: case 3833882: case 3244059: case 2654236: case 2064413: 
                    case 1474590: case 884767: case 294944: case 3833891: case 3244068: case 2654245: 
                    case 2064422: case 1474599: case 884776: case 294953: case 3833900: case 3244077: 
                    case 2654254: case 2064431: case 1474608: case 884785: case 294962: case 3833909: 
                    case 3244086: case 2654263: case 2064440: case 1474617: case 884794: case 294971: 
                        tfriend_mask3 += 2; continue;
                    case 3309568: case 2719745: case 2129922: case 1540099: case 950276: case 360453: 
                    case 3899400: case 3309577: case 2719754: case 2129931: case 1540108: case 950285: 
                    case 360462: case 3899409: case 3309586: case 2719763: case 2129940: case 1540117: 
                    case 950294: case 360471: case 3899418: case 3309595: case 2719772: case 2129949: 
                    case 1540126: case 950303: case 360480: case 3899427: case 3309604: case 2719781: 
                    case 2129958: case 1540135: case 950312: case 360489: case 3899436: case 3309613: 
                    case 2719790: case 2129967: case 1540144: case 950321: case 360498: case 3899445: 
                    case 3309622: case 2719799: case 2129976: case 1540153: case 950330: case 360507: 
                        tfriend_mask3 += 4; continue;
                    case 3375104: case 2785281: case 2195458: case 1605635: case 1015812: case 425989: 
                    case 3375113: case 2785290: case 2195467: case 1605644: case 1015821: case 425998: 
                    case 3375122: case 2785299: case 2195476: case 1605653: case 1015830: case 426007: 
                    case 3375131: case 2785308: case 2195485: case 1605662: case 1015839: case 426016: 
                    case 3375140: case 2785317: case 2195494: case 1605671: case 1015848: case 426025: 
                    case 3375149: case 2785326: case 2195503: case 1605680: case 1015857: case 426034: 
                    case 3375158: case 2785335: case 2195512: case 1605689: case 1015866: case 426043: 
                        tfriend_mask3 += 8; continue;
                    case 3440640: case 2850817: case 2260994: case 1671171: case 1081348: case 491525: 
                    case 3440649: case 2850826: case 2261003: case 1671180: case 1081357: case 491534: 
                    case 3440658: case 2850835: case 2261012: case 1671189: case 1081366: case 491543: 
                    case 3440667: case 2850844: case 2261021: case 1671198: case 1081375: case 491552: 
                    case 3440676: case 2850853: case 2261030: case 1671207: case 1081384: case 491561: 
                    case 3440685: case 2850862: case 2261039: case 1671216: case 1081393: case 491570: 
                    case 3440694: case 2850871: case 2261048: case 1671225: case 1081402: case 491579: 
                        tfriend_mask3 += 16; continue;
                    case 3506176: case 2916353: case 2326530: case 1736707: case 1146884: case 557061: 
                    case 3506185: case 2916362: case 2326539: case 1736716: case 1146893: case 557070: 
                    case 3506194: case 2916371: case 2326548: case 1736725: case 1146902: case 557079: 
                    case 3506203: case 2916380: case 2326557: case 1736734: case 1146911: case 557088: 
                    case 3506212: case 2916389: case 2326566: case 1736743: case 1146920: case 557097: 
                    case 3506221: case 2916398: case 2326575: case 1736752: case 1146929: case 557106: 
                    case 3506230: case 2916407: case 2326584: case 1736761: case 1146938: case 557115: 
                        tfriend_mask3 += 32; continue;
                    case 3571712: case 2981889: case 2392066: case 1802243: case 1212420: case 622597: 
                    case 32774: case 3571721: case 2981898: case 2392075: case 1802252: case 1212429: 
                    case 622606: case 32783: case 3571730: case 2981907: case 2392084: case 1802261: 
                    case 1212438: case 622615: case 32792: case 3571739: case 2981916: case 2392093: 
                    case 1802270: case 1212447: case 622624: case 32801: case 3571748: case 2981925: 
                    case 2392102: case 1802279: case 1212456: case 622633: case 32810: case 3571757: 
                    case 2981934: case 2392111: case 1802288: case 1212465: case 622642: case 32819: 
                    case 3571766: case 2981943: case 2392120: case 1802297: case 1212474: case 622651: 
                        tfriend_mask3 += 64; continue;
                    case 3637248: case 3047425: case 2457602: case 1867779: case 1277956: case 688133: 
                    case 98310: case 3637257: case 3047434: case 2457611: case 1867788: case 1277965: 
                    case 688142: case 98319: case 3637266: case 3047443: case 2457620: case 1867797: 
                    case 1277974: case 688151: case 98328: case 3637275: case 3047452: case 2457629: 
                    case 1867806: case 1277983: case 688160: case 98337: case 3637284: case 3047461: 
                    case 2457638: case 1867815: case 1277992: case 688169: case 98346: case 3637293: 
                    case 3047470: case 2457647: case 1867824: case 1278001: case 688178: case 98355: 
                    case 3637302: case 3047479: case 2457656: case 1867833: case 1278010: case 688187: 
                        tfriend_mask3 += 128; continue;
                    case 3702784: case 3112961: case 2523138: case 1933315: case 1343492: case 753669: 
                    case 163846: case 3702793: case 3112970: case 2523147: case 1933324: case 1343501: 
                    case 753678: case 163855: case 3702802: case 3112979: case 2523156: case 1933333: 
                    case 1343510: case 753687: case 163864: case 3702811: case 3112988: case 2523165: 
                    case 1933342: case 1343519: case 753696: case 163873: case 3702820: case 3112997: 
                    case 2523174: case 1933351: case 1343528: case 753705: case 163882: case 3702829: 
                    case 3113006: case 2523183: case 1933360: case 1343537: case 753714: case 163891: 
                    case 3702838: case 3113015: case 2523192: case 1933369: case 1343546: case 753723: 
                        tfriend_mask3 += 256; continue;
                    case 3768320: case 3178497: case 2588674: case 1998851: case 1409028: case 819205: 
                    case 229382: case 3768329: case 3178506: case 2588683: case 1998860: case 1409037: 
                    case 819214: case 229391: case 3768338: case 3178515: case 2588692: case 1998869: 
                    case 1409046: case 819223: case 229400: case 3768347: case 3178524: case 2588701: 
                    case 1998878: case 1409055: case 819232: case 229409: case 3768356: case 3178533: 
                    case 2588710: case 1998887: case 1409064: case 819241: case 229418: case 3768365: 
                    case 3178542: case 2588719: case 1998896: case 1409073: case 819250: case 229427: 
                    case 3768374: case 3178551: case 2588728: case 1998905: case 1409082: case 819259: 
                        tfriend_mask3 += 512; continue;
                    case 3833856: case 3244033: case 2654210: case 2064387: case 1474564: case 884741: 
                    case 294918: case 3833865: case 3244042: case 2654219: case 2064396: case 1474573: 
                    case 884750: case 294927: case 3833874: case 3244051: case 2654228: case 2064405: 
                    case 1474582: case 884759: case 294936: case 3833883: case 3244060: case 2654237: 
                    case 2064414: case 1474591: case 884768: case 294945: case 3833892: case 3244069: 
                    case 2654246: case 2064423: case 1474600: case 884777: case 294954: case 3833901: 
                    case 3244078: case 2654255: case 2064432: case 1474609: case 884786: case 294963: 
                    case 3833910: case 3244087: case 2654264: case 2064441: case 1474618: case 884795: 
                        tfriend_mask3 += 1024; continue;
                    case 3899392: case 3309569: case 2719746: case 2129923: case 1540100: case 950277: 
                    case 360454: case 3899401: case 3309578: case 2719755: case 2129932: case 1540109: 
                    case 950286: case 360463: case 3899410: case 3309587: case 2719764: case 2129941: 
                    case 1540118: case 950295: case 360472: case 3899419: case 3309596: case 2719773: 
                    case 2129950: case 1540127: case 950304: case 360481: case 3899428: case 3309605: 
                    case 2719782: case 2129959: case 1540136: case 950313: case 360490: case 3899437: 
                    case 3309614: case 2719791: case 2129968: case 1540145: case 950322: case 360499: 
                    case 3899446: case 3309623: case 2719800: case 2129977: case 1540154: case 950331: 
                        tfriend_mask3 += 2048; continue;
                    case 3375105: case 2785282: case 2195459: case 1605636: case 1015813: case 425990: 
                    case 3375114: case 2785291: case 2195468: case 1605645: case 1015822: case 425999: 
                    case 3375123: case 2785300: case 2195477: case 1605654: case 1015831: case 426008: 
                    case 3375132: case 2785309: case 2195486: case 1605663: case 1015840: case 426017: 
                    case 3375141: case 2785318: case 2195495: case 1605672: case 1015849: case 426026: 
                    case 3375150: case 2785327: case 2195504: case 1605681: case 1015858: case 426035: 
                    case 3375159: case 2785336: case 2195513: case 1605690: case 1015867: 
                        tfriend_mask3 += 4096; continue;
                    case 3440641: case 2850818: case 2260995: case 1671172: case 1081349: case 491526: 
                    case 3440650: case 2850827: case 2261004: case 1671181: case 1081358: case 491535: 
                    case 3440659: case 2850836: case 2261013: case 1671190: case 1081367: case 491544: 
                    case 3440668: case 2850845: case 2261022: case 1671199: case 1081376: case 491553: 
                    case 3440677: case 2850854: case 2261031: case 1671208: case 1081385: case 491562: 
                    case 3440686: case 2850863: case 2261040: case 1671217: case 1081394: case 491571: 
                    case 3440695: case 2850872: case 2261049: case 1671226: case 1081403: 
                        tfriend_mask3 += 8192; continue;
                    case 3506177: case 2916354: case 2326531: case 1736708: case 1146885: case 557062: 
                    case 3506186: case 2916363: case 2326540: case 1736717: case 1146894: case 557071: 
                    case 3506195: case 2916372: case 2326549: case 1736726: case 1146903: case 557080: 
                    case 3506204: case 2916381: case 2326558: case 1736735: case 1146912: case 557089: 
                    case 3506213: case 2916390: case 2326567: case 1736744: case 1146921: case 557098: 
                    case 3506222: case 2916399: case 2326576: case 1736753: case 1146930: case 557107: 
                    case 3506231: case 2916408: case 2326585: case 1736762: case 1146939: 
                        tfriend_mask3 += 16384; continue;
                    case 3571713: case 2981890: case 2392067: case 1802244: case 1212421: case 622598: 
                    case 32775: case 3571722: case 2981899: case 2392076: case 1802253: case 1212430: 
                    case 622607: case 32784: case 3571731: case 2981908: case 2392085: case 1802262: 
                    case 1212439: case 622616: case 32793: case 3571740: case 2981917: case 2392094: 
                    case 1802271: case 1212448: case 622625: case 32802: case 3571749: case 2981926: 
                    case 2392103: case 1802280: case 1212457: case 622634: case 32811: case 3571758: 
                    case 2981935: case 2392112: case 1802289: case 1212466: case 622643: case 32820: 
                    case 3571767: case 2981944: case 2392121: case 1802298: case 1212475: 
                        tfriend_mask3 += 16384;
                        tfriend_mask3 += 16384;
                        continue;
                    case 3637249: case 3047426: case 2457603: case 1867780: case 1277957: case 688134: 
                    case 98311: case 3637258: case 3047435: case 2457612: case 1867789: case 1277966: 
                    case 688143: case 98320: case 3637267: case 3047444: case 2457621: case 1867798: 
                    case 1277975: case 688152: case 98329: case 3637276: case 3047453: case 2457630: 
                    case 1867807: case 1277984: case 688161: case 98338: case 3637285: case 3047462: 
                    case 2457639: case 1867816: case 1277993: case 688170: case 98347: case 3637294: 
                    case 3047471: case 2457648: case 1867825: case 1278002: case 688179: case 98356: 
                    case 3637303: case 3047480: case 2457657: case 1867834: case 1278011: 
                        tfriend_mask4 += 1; continue;
                    case 3702785: case 3112962: case 2523139: case 1933316: case 1343493: case 753670: 
                    case 163847: case 3702794: case 3112971: case 2523148: case 1933325: case 1343502: 
                    case 753679: case 163856: case 3702803: case 3112980: case 2523157: case 1933334: 
                    case 1343511: case 753688: case 163865: case 3702812: case 3112989: case 2523166: 
                    case 1933343: case 1343520: case 753697: case 163874: case 3702821: case 3112998: 
                    case 2523175: case 1933352: case 1343529: case 753706: case 163883: case 3702830: 
                    case 3113007: case 2523184: case 1933361: case 1343538: case 753715: case 163892: 
                    case 3702839: case 3113016: case 2523193: case 1933370: case 1343547: 
                        tfriend_mask4 += 2; continue;
                    case 3768321: case 3178498: case 2588675: case 1998852: case 1409029: case 819206: 
                    case 229383: case 3768330: case 3178507: case 2588684: case 1998861: case 1409038: 
                    case 819215: case 229392: case 3768339: case 3178516: case 2588693: case 1998870: 
                    case 1409047: case 819224: case 229401: case 3768348: case 3178525: case 2588702: 
                    case 1998879: case 1409056: case 819233: case 229410: case 3768357: case 3178534: 
                    case 2588711: case 1998888: case 1409065: case 819242: case 229419: case 3768366: 
                    case 3178543: case 2588720: case 1998897: case 1409074: case 819251: case 229428: 
                    case 3768375: case 3178552: case 2588729: case 1998906: case 1409083: 
                        tfriend_mask4 += 4; continue;
                    case 3833857: case 3244034: case 2654211: case 2064388: case 1474565: case 884742: 
                    case 294919: case 3833866: case 3244043: case 2654220: case 2064397: case 1474574: 
                    case 884751: case 294928: case 3833875: case 3244052: case 2654229: case 2064406: 
                    case 1474583: case 884760: case 294937: case 3833884: case 3244061: case 2654238: 
                    case 2064415: case 1474592: case 884769: case 294946: case 3833893: case 3244070: 
                    case 2654247: case 2064424: case 1474601: case 884778: case 294955: case 3833902: 
                    case 3244079: case 2654256: case 2064433: case 1474610: case 884787: case 294964: 
                    case 3833911: case 3244088: case 2654265: case 2064442: case 1474619: 
                        tfriend_mask4 += 8; continue;
                    case 3899393: case 3309570: case 2719747: case 2129924: case 1540101: case 950278: 
                    case 360455: case 3899402: case 3309579: case 2719756: case 2129933: case 1540110: 
                    case 950287: case 360464: case 3899411: case 3309588: case 2719765: case 2129942: 
                    case 1540119: case 950296: case 360473: case 3899420: case 3309597: case 2719774: 
                    case 2129951: case 1540128: case 950305: case 360482: case 3899429: case 3309606: 
                    case 2719783: case 2129960: case 1540137: case 950314: case 360491: case 3899438: 
                    case 3309615: case 2719792: case 2129969: case 1540146: case 950323: case 360500: 
                    case 3899447: case 3309624: case 2719801: case 2129978: case 1540155: 
                        tfriend_mask4 += 16; continue;
                    case 3375106: case 2785283: case 2195460: case 1605637: case 1015814: case 425991: 
                    case 3375115: case 2785292: case 2195469: case 1605646: case 1015823: case 426000: 
                    case 3375124: case 2785301: case 2195478: case 1605655: case 1015832: case 426009: 
                    case 3375133: case 2785310: case 2195487: case 1605664: case 1015841: case 426018: 
                    case 3375142: case 2785319: case 2195496: case 1605673: case 1015850: case 426027: 
                    case 3375151: case 2785328: case 2195505: case 1605682: case 1015859: case 426036: 
                    case 3375160: case 2785337: case 2195514: case 1605691: 
                        tfriend_mask4 += 32; continue;
                    case 3440642: case 2850819: case 2260996: case 1671173: case 1081350: case 491527: 
                    case 3440651: case 2850828: case 2261005: case 1671182: case 1081359: case 491536: 
                    case 3440660: case 2850837: case 2261014: case 1671191: case 1081368: case 491545: 
                    case 3440669: case 2850846: case 2261023: case 1671200: case 1081377: case 491554: 
                    case 3440678: case 2850855: case 2261032: case 1671209: case 1081386: case 491563: 
                    case 3440687: case 2850864: case 2261041: case 1671218: case 1081395: case 491572: 
                    case 3440696: case 2850873: case 2261050: case 1671227: 
                        tfriend_mask4 += 64; continue;
                    case 3506178: case 2916355: case 2326532: case 1736709: case 1146886: case 557063: 
                    case 3506187: case 2916364: case 2326541: case 1736718: case 1146895: case 557072: 
                    case 3506196: case 2916373: case 2326550: case 1736727: case 1146904: case 557081: 
                    case 3506205: case 2916382: case 2326559: case 1736736: case 1146913: case 557090: 
                    case 3506214: case 2916391: case 2326568: case 1736745: case 1146922: case 557099: 
                    case 3506223: case 2916400: case 2326577: case 1736754: case 1146931: case 557108: 
                    case 3506232: case 2916409: case 2326586: case 1736763: 
                        tfriend_mask4 += 128; continue;
                    case 3571714: case 2981891: case 2392068: case 1802245: case 1212422: case 622599: 
                    case 32776: case 3571723: case 2981900: case 2392077: case 1802254: case 1212431: 
                    case 622608: case 32785: case 3571732: case 2981909: case 2392086: case 1802263: 
                    case 1212440: case 622617: case 32794: case 3571741: case 2981918: case 2392095: 
                    case 1802272: case 1212449: case 622626: case 32803: case 3571750: case 2981927: 
                    case 2392104: case 1802281: case 1212458: case 622635: case 32812: case 3571759: 
                    case 2981936: case 2392113: case 1802290: case 1212467: case 622644: case 32821: 
                    case 3571768: case 2981945: case 2392122: case 1802299: 
                        tfriend_mask4 += 256; continue;
                    case 3637250: case 3047427: case 2457604: case 1867781: case 1277958: case 688135: 
                    case 98312: case 3637259: case 3047436: case 2457613: case 1867790: case 1277967: 
                    case 688144: case 98321: case 3637268: case 3047445: case 2457622: case 1867799: 
                    case 1277976: case 688153: case 98330: case 3637277: case 3047454: case 2457631: 
                    case 1867808: case 1277985: case 688162: case 98339: case 3637286: case 3047463: 
                    case 2457640: case 1867817: case 1277994: case 688171: case 98348: case 3637295: 
                    case 3047472: case 2457649: case 1867826: case 1278003: case 688180: case 98357: 
                    case 3637304: case 3047481: case 2457658: case 1867835: 
                        tfriend_mask4 += 512; continue;
                    case 3702786: case 3112963: case 2523140: case 1933317: case 1343494: case 753671: 
                    case 163848: case 3702795: case 3112972: case 2523149: case 1933326: case 1343503: 
                    case 753680: case 163857: case 3702804: case 3112981: case 2523158: case 1933335: 
                    case 1343512: case 753689: case 163866: case 3702813: case 3112990: case 2523167: 
                    case 1933344: case 1343521: case 753698: case 163875: case 3702822: case 3112999: 
                    case 2523176: case 1933353: case 1343530: case 753707: case 163884: case 3702831: 
                    case 3113008: case 2523185: case 1933362: case 1343539: case 753716: case 163893: 
                    case 3702840: case 3113017: case 2523194: case 1933371: 
                        tfriend_mask4 += 1024; continue;
                    case 3768322: case 3178499: case 2588676: case 1998853: case 1409030: case 819207: 
                    case 229384: case 3768331: case 3178508: case 2588685: case 1998862: case 1409039: 
                    case 819216: case 229393: case 3768340: case 3178517: case 2588694: case 1998871: 
                    case 1409048: case 819225: case 229402: case 3768349: case 3178526: case 2588703: 
                    case 1998880: case 1409057: case 819234: case 229411: case 3768358: case 3178535: 
                    case 2588712: case 1998889: case 1409066: case 819243: case 229420: case 3768367: 
                    case 3178544: case 2588721: case 1998898: case 1409075: case 819252: case 229429: 
                    case 3768376: case 3178553: case 2588730: case 1998907: 
                        tfriend_mask4 += 2048; continue;
                    case 3833858: case 3244035: case 2654212: case 2064389: case 1474566: case 884743: 
                    case 294920: case 3833867: case 3244044: case 2654221: case 2064398: case 1474575: 
                    case 884752: case 294929: case 3833876: case 3244053: case 2654230: case 2064407: 
                    case 1474584: case 884761: case 294938: case 3833885: case 3244062: case 2654239: 
                    case 2064416: case 1474593: case 884770: case 294947: case 3833894: case 3244071: 
                    case 2654248: case 2064425: case 1474602: case 884779: case 294956: case 3833903: 
                    case 3244080: case 2654257: case 2064434: case 1474611: case 884788: case 294965: 
                    case 3833912: case 3244089: case 2654266: case 2064443: 
                        tfriend_mask4 += 4096; continue;
                    case 3899394: case 3309571: case 2719748: case 2129925: case 1540102: case 950279: 
                    case 360456: case 3899403: case 3309580: case 2719757: case 2129934: case 1540111: 
                    case 950288: case 360465: case 3899412: case 3309589: case 2719766: case 2129943: 
                    case 1540120: case 950297: case 360474: case 3899421: case 3309598: case 2719775: 
                    case 2129952: case 1540129: case 950306: case 360483: case 3899430: case 3309607: 
                    case 2719784: case 2129961: case 1540138: case 950315: case 360492: case 3899439: 
                    case 3309616: case 2719793: case 2129970: case 1540147: case 950324: case 360501: 
                    case 3899448: case 3309625: case 2719802: case 2129979: 
                        tfriend_mask4 += 8192; continue;
                    case 3375107: case 2785284: case 2195461: case 1605638: case 1015815: case 425992: 
                    case 3375116: case 2785293: case 2195470: case 1605647: case 1015824: case 426001: 
                    case 3375125: case 2785302: case 2195479: case 1605656: case 1015833: case 426010: 
                    case 3375134: case 2785311: case 2195488: case 1605665: case 1015842: case 426019: 
                    case 3375143: case 2785320: case 2195497: case 1605674: case 1015851: case 426028: 
                    case 3375152: case 2785329: case 2195506: case 1605683: case 1015860: case 426037: 
                    case 3375161: case 2785338: case 2195515: 
                        tfriend_mask4 += 16384; continue;
                    case 3440643: case 2850820: case 2260997: case 1671174: case 1081351: case 491528: 
                    case 3440652: case 2850829: case 2261006: case 1671183: case 1081360: case 491537: 
                    case 3440661: case 2850838: case 2261015: case 1671192: case 1081369: case 491546: 
                    case 3440670: case 2850847: case 2261024: case 1671201: case 1081378: case 491555: 
                    case 3440679: case 2850856: case 2261033: case 1671210: case 1081387: case 491564: 
                    case 3440688: case 2850865: case 2261042: case 1671219: case 1081396: case 491573: 
                    case 3440697: case 2850874: case 2261051: 
                        tfriend_mask4 += 16384;
                        tfriend_mask4 += 16384;
                        continue;
                    case 3506179: case 2916356: case 2326533: case 1736710: case 1146887: case 557064: 
                    case 3506188: case 2916365: case 2326542: case 1736719: case 1146896: case 557073: 
                    case 3506197: case 2916374: case 2326551: case 1736728: case 1146905: case 557082: 
                    case 3506206: case 2916383: case 2326560: case 1736737: case 1146914: case 557091: 
                    case 3506215: case 2916392: case 2326569: case 1736746: case 1146923: case 557100: 
                    case 3506224: case 2916401: case 2326578: case 1736755: case 1146932: case 557109: 
                    case 3506233: case 2916410: case 2326587: 
                        tfriend_mask5 += 1; continue;
                    default: continue;
                }
                default: continue;
            }
        }
        store_friends(
            tfriend_mask0,
            tfriend_mask1,
            tfriend_mask2,
            tfriend_mask3,
            tfriend_mask4,
            tfriend_mask5
        );
    }

    void store_friends(
        int tfriend_mask0,
        int tfriend_mask1,
        int tfriend_mask2,
        int tfriend_mask3,
        int tfriend_mask4,
        int tfriend_mask5
    ) throws GameActionException {
        MapLocation myloc = rc.getLocation();
        long lfriend_mask0 = tfriend_mask0;
        long lfriend_mask1 = tfriend_mask1;
        long lfriend_mask2 = tfriend_mask2;
        long lfriend_mask3 = tfriend_mask3;
        long lfriend_mask4 = tfriend_mask4;
        long lfriend_mask5 = tfriend_mask5;
        outer: switch (myloc.y % 9) {
            case 0:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask0 << 60) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27) | (lfriend_mask4 << 43) | (lfriend_mask5 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 4) | (lfriend_mask1 << 12) | (lfriend_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask0 << 61) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28) | (lfriend_mask4 << 44) | (lfriend_mask5 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 3) | (lfriend_mask1 << 13) | (lfriend_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask0 << 62) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29) | (lfriend_mask4 << 45) | (lfriend_mask5 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 2) | (lfriend_mask1 << 14) | (lfriend_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask0 << 63) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30) | (lfriend_mask4 << 46) | (lfriend_mask5 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 1) | (lfriend_mask1 << 15) | (lfriend_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask2 << 15) | (lfriend_mask3 << 31) | (lfriend_mask4 << 47) | (lfriend_mask5 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0) | (lfriend_mask1 << 16) | (lfriend_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask2 << 16) | (lfriend_mask3 << 32) | (lfriend_mask4 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 1) | (lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask5)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask2 << 17) | (lfriend_mask3 << 33) | (lfriend_mask4 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 2) | (lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask4 >>> 15) | (lfriend_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask2 << 18) | (lfriend_mask3 << 34) | (lfriend_mask4 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 3) | (lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask4 >>> 14) | (lfriend_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask2 << 19) | (lfriend_mask3 << 35) | (lfriend_mask4 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 4) | (lfriend_mask1 << 20) | (lfriend_mask4 >>> 13) | (lfriend_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask2 << 20) | (lfriend_mask3 << 36) | (lfriend_mask4 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask4 >>> 12) | (lfriend_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask1 << 5) | (lfriend_mask2 << 21) | (lfriend_mask3 << 37) | (lfriend_mask4 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask4 >>> 11) | (lfriend_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask1 << 6) | (lfriend_mask2 << 22) | (lfriend_mask3 << 38) | (lfriend_mask4 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask4 >>> 10) | (lfriend_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask1 << 7) | (lfriend_mask2 << 23) | (lfriend_mask3 << 39) | (lfriend_mask4 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask4 >>> 9) | (lfriend_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask1 << 8) | (lfriend_mask2 << 24) | (lfriend_mask3 << 40) | (lfriend_mask4 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask4 >>> 8) | (lfriend_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask1 << 9) | (lfriend_mask2 << 25) | (lfriend_mask3 << 41) | (lfriend_mask4 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask4 >>> 7) | (lfriend_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask1 << 10) | (lfriend_mask2 << 26) | (lfriend_mask3 << 42) | (lfriend_mask4 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask4 >>> 6) | (lfriend_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask1 << 11) | (lfriend_mask2 << 27) | (lfriend_mask3 << 43) | (lfriend_mask4 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask4 >>> 5) | (lfriend_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask1 << 12) | (lfriend_mask2 << 28) | (lfriend_mask3 << 44) | (lfriend_mask4 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask4 >>> 4) | (lfriend_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask1 << 13) | (lfriend_mask2 << 29) | (lfriend_mask3 << 45) | (lfriend_mask4 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask4 >>> 3) | (lfriend_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask1 << 14) | (lfriend_mask2 << 30) | (lfriend_mask3 << 46) | (lfriend_mask4 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask4 >>> 2) | (lfriend_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask1 << 15) | (lfriend_mask2 << 31) | (lfriend_mask3 << 47) | (lfriend_mask4 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask4 >>> 1) | (lfriend_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask1 << 16) | (lfriend_mask2 << 32) | (lfriend_mask3 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask4) | (lfriend_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask3 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask3 >>> 15) | (lfriend_mask4 << 1) | (lfriend_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask3 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask3 >>> 14) | (lfriend_mask4 << 2) | (lfriend_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask3 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 20) | (lfriend_mask3 >>> 13) | (lfriend_mask4 << 3) | (lfriend_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask1 << 20) | (lfriend_mask2 << 36) | (lfriend_mask3 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 21) | (lfriend_mask3 >>> 12) | (lfriend_mask4 << 4) | (lfriend_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask2 << 37) | (lfriend_mask3 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 22) | (lfriend_mask3 >>> 11) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask2 << 38) | (lfriend_mask3 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 23) | (lfriend_mask3 >>> 10) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask2 << 39) | (lfriend_mask3 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 24) | (lfriend_mask3 >>> 9) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask2 << 40) | (lfriend_mask3 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 25) | (lfriend_mask3 >>> 8) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask2 << 41) | (lfriend_mask3 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 26) | (lfriend_mask3 >>> 7) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask2 << 42) | (lfriend_mask3 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 27) | (lfriend_mask3 >>> 6) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask2 << 43) | (lfriend_mask3 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 28) | (lfriend_mask3 >>> 5) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask2 << 44) | (lfriend_mask3 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 29) | (lfriend_mask3 >>> 4) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask2 << 45) | (lfriend_mask3 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 30) | (lfriend_mask3 >>> 3) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask2 << 46) | (lfriend_mask3 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 31) | (lfriend_mask3 >>> 2) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask2 << 47) | (lfriend_mask3 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 32) | (lfriend_mask3 >>> 1) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask2 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 33) | (lfriend_mask3) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask2 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 34) | (lfriend_mask2 >>> 15) | (lfriend_mask3 << 1) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask2 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 35) | (lfriend_mask2 >>> 14) | (lfriend_mask3 << 2) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask2 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 13) | (lfriend_mask3 << 3) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask0 << 20) | (lfriend_mask1 << 36) | (lfriend_mask2 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 12) | (lfriend_mask3 << 4) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask0 << 21) | (lfriend_mask1 << 37) | (lfriend_mask2 << 53) | (lfriend_mask5 << 20)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 11) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask0 << 22) | (lfriend_mask1 << 38) | (lfriend_mask2 << 54) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 10) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask0 << 23) | (lfriend_mask1 << 39) | (lfriend_mask2 << 55) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 9) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask0 << 24) | (lfriend_mask1 << 40) | (lfriend_mask2 << 56) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 8) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask0 << 25) | (lfriend_mask1 << 41) | (lfriend_mask2 << 57) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 7) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask0 << 26) | (lfriend_mask1 << 42) | (lfriend_mask2 << 58) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 6) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask0 << 27) | (lfriend_mask1 << 43) | (lfriend_mask2 << 59) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 5) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask0 << 28) | (lfriend_mask1 << 44) | (lfriend_mask2 << 60) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 4) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask0 << 29) | (lfriend_mask1 << 45) | (lfriend_mask2 << 61) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 3) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask0 << 30) | (lfriend_mask1 << 46) | (lfriend_mask2 << 62) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 2) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask0 << 31) | (lfriend_mask1 << 47) | (lfriend_mask2 << 63) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 1) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask0 << 32) | (lfriend_mask1 << 48) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask0 << 33) | (lfriend_mask1 << 49) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 15) | (lfriend_mask2 << 1) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask0 << 34) | (lfriend_mask1 << 50) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 14) | (lfriend_mask2 << 2) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask0 << 35) | (lfriend_mask1 << 51) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 13) | (lfriend_mask2 << 3) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask0 << 36) | (lfriend_mask1 << 52) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 12) | (lfriend_mask2 << 4) | (lfriend_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask0 << 37) | (lfriend_mask1 << 53) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 11) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask0 << 38) | (lfriend_mask1 << 54) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21) | (lfriend_mask5 << 37)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 10) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 1:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask2 << 20) | (lfriend_mask3 << 36) | (lfriend_mask4 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask4 >>> 12) | (lfriend_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask1 << 5) | (lfriend_mask2 << 21) | (lfriend_mask3 << 37) | (lfriend_mask4 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask4 >>> 11) | (lfriend_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask1 << 6) | (lfriend_mask2 << 22) | (lfriend_mask3 << 38) | (lfriend_mask4 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask4 >>> 10) | (lfriend_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask1 << 7) | (lfriend_mask2 << 23) | (lfriend_mask3 << 39) | (lfriend_mask4 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask4 >>> 9) | (lfriend_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask1 << 8) | (lfriend_mask2 << 24) | (lfriend_mask3 << 40) | (lfriend_mask4 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask4 >>> 8) | (lfriend_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask1 << 9) | (lfriend_mask2 << 25) | (lfriend_mask3 << 41) | (lfriend_mask4 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask4 >>> 7) | (lfriend_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask1 << 10) | (lfriend_mask2 << 26) | (lfriend_mask3 << 42) | (lfriend_mask4 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask4 >>> 6) | (lfriend_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask1 << 11) | (lfriend_mask2 << 27) | (lfriend_mask3 << 43) | (lfriend_mask4 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask4 >>> 5) | (lfriend_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask1 << 12) | (lfriend_mask2 << 28) | (lfriend_mask3 << 44) | (lfriend_mask4 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask4 >>> 4) | (lfriend_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask1 << 13) | (lfriend_mask2 << 29) | (lfriend_mask3 << 45) | (lfriend_mask4 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask4 >>> 3) | (lfriend_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask1 << 14) | (lfriend_mask2 << 30) | (lfriend_mask3 << 46) | (lfriend_mask4 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask4 >>> 2) | (lfriend_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask1 << 15) | (lfriend_mask2 << 31) | (lfriend_mask3 << 47) | (lfriend_mask4 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask4 >>> 1) | (lfriend_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask1 << 16) | (lfriend_mask2 << 32) | (lfriend_mask3 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask4) | (lfriend_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask3 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask3 >>> 15) | (lfriend_mask4 << 1) | (lfriend_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask3 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask3 >>> 14) | (lfriend_mask4 << 2) | (lfriend_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask3 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 20) | (lfriend_mask3 >>> 13) | (lfriend_mask4 << 3) | (lfriend_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask1 << 20) | (lfriend_mask2 << 36) | (lfriend_mask3 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 21) | (lfriend_mask3 >>> 12) | (lfriend_mask4 << 4) | (lfriend_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask2 << 37) | (lfriend_mask3 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 22) | (lfriend_mask3 >>> 11) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask2 << 38) | (lfriend_mask3 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 23) | (lfriend_mask3 >>> 10) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask2 << 39) | (lfriend_mask3 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 24) | (lfriend_mask3 >>> 9) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask2 << 40) | (lfriend_mask3 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 25) | (lfriend_mask3 >>> 8) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask2 << 41) | (lfriend_mask3 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 26) | (lfriend_mask3 >>> 7) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask2 << 42) | (lfriend_mask3 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 27) | (lfriend_mask3 >>> 6) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask2 << 43) | (lfriend_mask3 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 28) | (lfriend_mask3 >>> 5) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask2 << 44) | (lfriend_mask3 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 29) | (lfriend_mask3 >>> 4) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask2 << 45) | (lfriend_mask3 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 30) | (lfriend_mask3 >>> 3) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask2 << 46) | (lfriend_mask3 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 31) | (lfriend_mask3 >>> 2) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask2 << 47) | (lfriend_mask3 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 32) | (lfriend_mask3 >>> 1) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask2 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 33) | (lfriend_mask3) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask2 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 34) | (lfriend_mask2 >>> 15) | (lfriend_mask3 << 1) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask2 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 35) | (lfriend_mask2 >>> 14) | (lfriend_mask3 << 2) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask2 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 13) | (lfriend_mask3 << 3) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask0 << 20) | (lfriend_mask1 << 36) | (lfriend_mask2 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 12) | (lfriend_mask3 << 4) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask0 << 21) | (lfriend_mask1 << 37) | (lfriend_mask2 << 53) | (lfriend_mask5 << 20)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 11) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask0 << 22) | (lfriend_mask1 << 38) | (lfriend_mask2 << 54) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 10) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask0 << 23) | (lfriend_mask1 << 39) | (lfriend_mask2 << 55) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 9) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask0 << 24) | (lfriend_mask1 << 40) | (lfriend_mask2 << 56) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 8) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask0 << 25) | (lfriend_mask1 << 41) | (lfriend_mask2 << 57) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 7) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask0 << 26) | (lfriend_mask1 << 42) | (lfriend_mask2 << 58) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 6) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask0 << 27) | (lfriend_mask1 << 43) | (lfriend_mask2 << 59) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 5) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask0 << 28) | (lfriend_mask1 << 44) | (lfriend_mask2 << 60) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 4) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask0 << 29) | (lfriend_mask1 << 45) | (lfriend_mask2 << 61) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 3) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask0 << 30) | (lfriend_mask1 << 46) | (lfriend_mask2 << 62) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 2) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask0 << 31) | (lfriend_mask1 << 47) | (lfriend_mask2 << 63) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 1) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask0 << 32) | (lfriend_mask1 << 48) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask0 << 33) | (lfriend_mask1 << 49) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 15) | (lfriend_mask2 << 1) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask0 << 34) | (lfriend_mask1 << 50) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 14) | (lfriend_mask2 << 2) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask0 << 35) | (lfriend_mask1 << 51) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 13) | (lfriend_mask2 << 3) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask0 << 36) | (lfriend_mask1 << 52) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 12) | (lfriend_mask2 << 4) | (lfriend_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask0 << 37) | (lfriend_mask1 << 53) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 11) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask0 << 38) | (lfriend_mask1 << 54) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21) | (lfriend_mask5 << 37)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 10) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask0 << 39) | (lfriend_mask1 << 55) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22) | (lfriend_mask5 << 38)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 9) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask0 << 40) | (lfriend_mask1 << 56) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23) | (lfriend_mask5 << 39)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 8) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask0 << 41) | (lfriend_mask1 << 57) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24) | (lfriend_mask5 << 40)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 7) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask0 << 42) | (lfriend_mask1 << 58) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25) | (lfriend_mask5 << 41)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 6) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask0 << 43) | (lfriend_mask1 << 59) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26) | (lfriend_mask5 << 42)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 5) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask0 << 44) | (lfriend_mask1 << 60) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27) | (lfriend_mask5 << 43)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 4) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask0 << 45) | (lfriend_mask1 << 61) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28) | (lfriend_mask5 << 44)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 3) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask0 << 46) | (lfriend_mask1 << 62) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29) | (lfriend_mask5 << 45)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 2) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask0 << 47) | (lfriend_mask1 << 63) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30) | (lfriend_mask5 << 46)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 1) | (lfriend_mask2 << 15) | (lfriend_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 2:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask1 << 13) | (lfriend_mask2 << 29) | (lfriend_mask3 << 45) | (lfriend_mask4 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask4 >>> 3) | (lfriend_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask1 << 14) | (lfriend_mask2 << 30) | (lfriend_mask3 << 46) | (lfriend_mask4 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask4 >>> 2) | (lfriend_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask1 << 15) | (lfriend_mask2 << 31) | (lfriend_mask3 << 47) | (lfriend_mask4 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask4 >>> 1) | (lfriend_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask1 << 16) | (lfriend_mask2 << 32) | (lfriend_mask3 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask4) | (lfriend_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask3 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask3 >>> 15) | (lfriend_mask4 << 1) | (lfriend_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask3 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask3 >>> 14) | (lfriend_mask4 << 2) | (lfriend_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask3 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 20) | (lfriend_mask3 >>> 13) | (lfriend_mask4 << 3) | (lfriend_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask1 << 20) | (lfriend_mask2 << 36) | (lfriend_mask3 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 21) | (lfriend_mask3 >>> 12) | (lfriend_mask4 << 4) | (lfriend_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask2 << 37) | (lfriend_mask3 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 22) | (lfriend_mask3 >>> 11) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask2 << 38) | (lfriend_mask3 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 23) | (lfriend_mask3 >>> 10) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask2 << 39) | (lfriend_mask3 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 24) | (lfriend_mask3 >>> 9) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask2 << 40) | (lfriend_mask3 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 25) | (lfriend_mask3 >>> 8) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask2 << 41) | (lfriend_mask3 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 26) | (lfriend_mask3 >>> 7) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask2 << 42) | (lfriend_mask3 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 27) | (lfriend_mask3 >>> 6) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask2 << 43) | (lfriend_mask3 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 28) | (lfriend_mask3 >>> 5) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask2 << 44) | (lfriend_mask3 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 29) | (lfriend_mask3 >>> 4) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask2 << 45) | (lfriend_mask3 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 30) | (lfriend_mask3 >>> 3) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask2 << 46) | (lfriend_mask3 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 31) | (lfriend_mask3 >>> 2) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask2 << 47) | (lfriend_mask3 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 32) | (lfriend_mask3 >>> 1) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask2 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 33) | (lfriend_mask3) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask2 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 34) | (lfriend_mask2 >>> 15) | (lfriend_mask3 << 1) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask2 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 35) | (lfriend_mask2 >>> 14) | (lfriend_mask3 << 2) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask2 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 13) | (lfriend_mask3 << 3) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask0 << 20) | (lfriend_mask1 << 36) | (lfriend_mask2 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 12) | (lfriend_mask3 << 4) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask0 << 21) | (lfriend_mask1 << 37) | (lfriend_mask2 << 53) | (lfriend_mask5 << 20)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 11) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask0 << 22) | (lfriend_mask1 << 38) | (lfriend_mask2 << 54) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 10) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask0 << 23) | (lfriend_mask1 << 39) | (lfriend_mask2 << 55) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 9) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask0 << 24) | (lfriend_mask1 << 40) | (lfriend_mask2 << 56) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 8) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask0 << 25) | (lfriend_mask1 << 41) | (lfriend_mask2 << 57) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 7) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask0 << 26) | (lfriend_mask1 << 42) | (lfriend_mask2 << 58) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 6) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask0 << 27) | (lfriend_mask1 << 43) | (lfriend_mask2 << 59) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 5) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask0 << 28) | (lfriend_mask1 << 44) | (lfriend_mask2 << 60) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 4) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask0 << 29) | (lfriend_mask1 << 45) | (lfriend_mask2 << 61) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 3) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask0 << 30) | (lfriend_mask1 << 46) | (lfriend_mask2 << 62) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 2) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask0 << 31) | (lfriend_mask1 << 47) | (lfriend_mask2 << 63) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 1) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask0 << 32) | (lfriend_mask1 << 48) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask0 << 33) | (lfriend_mask1 << 49) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 15) | (lfriend_mask2 << 1) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask0 << 34) | (lfriend_mask1 << 50) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 14) | (lfriend_mask2 << 2) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask0 << 35) | (lfriend_mask1 << 51) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 13) | (lfriend_mask2 << 3) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask0 << 36) | (lfriend_mask1 << 52) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 12) | (lfriend_mask2 << 4) | (lfriend_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask0 << 37) | (lfriend_mask1 << 53) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 11) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask0 << 38) | (lfriend_mask1 << 54) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21) | (lfriend_mask5 << 37)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 10) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask0 << 39) | (lfriend_mask1 << 55) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22) | (lfriend_mask5 << 38)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 9) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask0 << 40) | (lfriend_mask1 << 56) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23) | (lfriend_mask5 << 39)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 8) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask0 << 41) | (lfriend_mask1 << 57) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24) | (lfriend_mask5 << 40)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 7) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask0 << 42) | (lfriend_mask1 << 58) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25) | (lfriend_mask5 << 41)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 6) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask0 << 43) | (lfriend_mask1 << 59) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26) | (lfriend_mask5 << 42)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 5) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask0 << 44) | (lfriend_mask1 << 60) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27) | (lfriend_mask5 << 43)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 4) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask0 << 45) | (lfriend_mask1 << 61) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28) | (lfriend_mask5 << 44)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 3) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask0 << 46) | (lfriend_mask1 << 62) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29) | (lfriend_mask5 << 45)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 2) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask0 << 47) | (lfriend_mask1 << 63) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30) | (lfriend_mask5 << 46)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 1) | (lfriend_mask2 << 15) | (lfriend_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask0 << 48) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31) | (lfriend_mask5 << 47)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1) | (lfriend_mask2 << 16) | (lfriend_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask0 << 49) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32) | (lfriend_mask5 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 15) | (lfriend_mask1 << 1) | (lfriend_mask2 << 17) | (lfriend_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask0 << 50) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33) | (lfriend_mask5 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 14) | (lfriend_mask1 << 2) | (lfriend_mask2 << 18) | (lfriend_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask0 << 51) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34) | (lfriend_mask5 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 13) | (lfriend_mask1 << 3) | (lfriend_mask2 << 19) | (lfriend_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask0 << 52) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35) | (lfriend_mask5 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 12) | (lfriend_mask1 << 4) | (lfriend_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask0 << 53) | (lfriend_mask3 << 20) | (lfriend_mask4 << 36) | (lfriend_mask5 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 11) | (lfriend_mask1 << 5) | (lfriend_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask0 << 54) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21) | (lfriend_mask4 << 37) | (lfriend_mask5 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 10) | (lfriend_mask1 << 6) | (lfriend_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask0 << 55) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22) | (lfriend_mask4 << 38) | (lfriend_mask5 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 9) | (lfriend_mask1 << 7) | (lfriend_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask0 << 56) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23) | (lfriend_mask4 << 39) | (lfriend_mask5 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 8) | (lfriend_mask1 << 8) | (lfriend_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 3:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask2 << 38) | (lfriend_mask3 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 23) | (lfriend_mask3 >>> 10) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask2 << 39) | (lfriend_mask3 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 24) | (lfriend_mask3 >>> 9) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask2 << 40) | (lfriend_mask3 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 25) | (lfriend_mask3 >>> 8) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask2 << 41) | (lfriend_mask3 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 26) | (lfriend_mask3 >>> 7) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask2 << 42) | (lfriend_mask3 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 27) | (lfriend_mask3 >>> 6) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask2 << 43) | (lfriend_mask3 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 28) | (lfriend_mask3 >>> 5) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask2 << 44) | (lfriend_mask3 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 29) | (lfriend_mask3 >>> 4) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask2 << 45) | (lfriend_mask3 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 30) | (lfriend_mask3 >>> 3) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask2 << 46) | (lfriend_mask3 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 31) | (lfriend_mask3 >>> 2) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask2 << 47) | (lfriend_mask3 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 32) | (lfriend_mask3 >>> 1) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask2 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 33) | (lfriend_mask3) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask2 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 34) | (lfriend_mask2 >>> 15) | (lfriend_mask3 << 1) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask2 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 35) | (lfriend_mask2 >>> 14) | (lfriend_mask3 << 2) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask2 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 13) | (lfriend_mask3 << 3) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask0 << 20) | (lfriend_mask1 << 36) | (lfriend_mask2 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 12) | (lfriend_mask3 << 4) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask0 << 21) | (lfriend_mask1 << 37) | (lfriend_mask2 << 53) | (lfriend_mask5 << 20)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 11) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask0 << 22) | (lfriend_mask1 << 38) | (lfriend_mask2 << 54) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 10) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask0 << 23) | (lfriend_mask1 << 39) | (lfriend_mask2 << 55) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 9) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask0 << 24) | (lfriend_mask1 << 40) | (lfriend_mask2 << 56) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 8) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask0 << 25) | (lfriend_mask1 << 41) | (lfriend_mask2 << 57) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 7) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask0 << 26) | (lfriend_mask1 << 42) | (lfriend_mask2 << 58) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 6) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask0 << 27) | (lfriend_mask1 << 43) | (lfriend_mask2 << 59) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 5) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask0 << 28) | (lfriend_mask1 << 44) | (lfriend_mask2 << 60) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 4) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask0 << 29) | (lfriend_mask1 << 45) | (lfriend_mask2 << 61) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 3) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask0 << 30) | (lfriend_mask1 << 46) | (lfriend_mask2 << 62) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 2) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask0 << 31) | (lfriend_mask1 << 47) | (lfriend_mask2 << 63) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 1) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask0 << 32) | (lfriend_mask1 << 48) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask0 << 33) | (lfriend_mask1 << 49) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 15) | (lfriend_mask2 << 1) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask0 << 34) | (lfriend_mask1 << 50) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 14) | (lfriend_mask2 << 2) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask0 << 35) | (lfriend_mask1 << 51) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 13) | (lfriend_mask2 << 3) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask0 << 36) | (lfriend_mask1 << 52) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 12) | (lfriend_mask2 << 4) | (lfriend_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask0 << 37) | (lfriend_mask1 << 53) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 11) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask0 << 38) | (lfriend_mask1 << 54) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21) | (lfriend_mask5 << 37)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 10) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask0 << 39) | (lfriend_mask1 << 55) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22) | (lfriend_mask5 << 38)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 9) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask0 << 40) | (lfriend_mask1 << 56) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23) | (lfriend_mask5 << 39)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 8) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask0 << 41) | (lfriend_mask1 << 57) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24) | (lfriend_mask5 << 40)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 7) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask0 << 42) | (lfriend_mask1 << 58) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25) | (lfriend_mask5 << 41)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 6) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask0 << 43) | (lfriend_mask1 << 59) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26) | (lfriend_mask5 << 42)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 5) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask0 << 44) | (lfriend_mask1 << 60) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27) | (lfriend_mask5 << 43)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 4) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask0 << 45) | (lfriend_mask1 << 61) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28) | (lfriend_mask5 << 44)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 3) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask0 << 46) | (lfriend_mask1 << 62) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29) | (lfriend_mask5 << 45)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 2) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask0 << 47) | (lfriend_mask1 << 63) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30) | (lfriend_mask5 << 46)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 1) | (lfriend_mask2 << 15) | (lfriend_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask0 << 48) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31) | (lfriend_mask5 << 47)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1) | (lfriend_mask2 << 16) | (lfriend_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask0 << 49) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32) | (lfriend_mask5 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 15) | (lfriend_mask1 << 1) | (lfriend_mask2 << 17) | (lfriend_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask0 << 50) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33) | (lfriend_mask5 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 14) | (lfriend_mask1 << 2) | (lfriend_mask2 << 18) | (lfriend_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask0 << 51) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34) | (lfriend_mask5 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 13) | (lfriend_mask1 << 3) | (lfriend_mask2 << 19) | (lfriend_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask0 << 52) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35) | (lfriend_mask5 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 12) | (lfriend_mask1 << 4) | (lfriend_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask0 << 53) | (lfriend_mask3 << 20) | (lfriend_mask4 << 36) | (lfriend_mask5 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 11) | (lfriend_mask1 << 5) | (lfriend_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask0 << 54) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21) | (lfriend_mask4 << 37) | (lfriend_mask5 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 10) | (lfriend_mask1 << 6) | (lfriend_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask0 << 55) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22) | (lfriend_mask4 << 38) | (lfriend_mask5 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 9) | (lfriend_mask1 << 7) | (lfriend_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask0 << 56) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23) | (lfriend_mask4 << 39) | (lfriend_mask5 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 8) | (lfriend_mask1 << 8) | (lfriend_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask0 << 57) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24) | (lfriend_mask4 << 40) | (lfriend_mask5 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 7) | (lfriend_mask1 << 9) | (lfriend_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask0 << 58) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25) | (lfriend_mask4 << 41) | (lfriend_mask5 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 6) | (lfriend_mask1 << 10) | (lfriend_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask0 << 59) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26) | (lfriend_mask4 << 42) | (lfriend_mask5 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 5) | (lfriend_mask1 << 11) | (lfriend_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask0 << 60) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27) | (lfriend_mask4 << 43) | (lfriend_mask5 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 4) | (lfriend_mask1 << 12) | (lfriend_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask0 << 61) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28) | (lfriend_mask4 << 44) | (lfriend_mask5 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 3) | (lfriend_mask1 << 13) | (lfriend_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask0 << 62) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29) | (lfriend_mask4 << 45) | (lfriend_mask5 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 2) | (lfriend_mask1 << 14) | (lfriend_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask0 << 63) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30) | (lfriend_mask4 << 46) | (lfriend_mask5 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 1) | (lfriend_mask1 << 15) | (lfriend_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask2 << 15) | (lfriend_mask3 << 31) | (lfriend_mask4 << 47) | (lfriend_mask5 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0) | (lfriend_mask1 << 16) | (lfriend_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask2 << 16) | (lfriend_mask3 << 32) | (lfriend_mask4 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 1) | (lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask5)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 4:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask2 << 47) | (lfriend_mask3 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 32) | (lfriend_mask3 >>> 1) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask2 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 33) | (lfriend_mask3) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask2 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 34) | (lfriend_mask2 >>> 15) | (lfriend_mask3 << 1) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask2 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 35) | (lfriend_mask2 >>> 14) | (lfriend_mask3 << 2) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask2 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 13) | (lfriend_mask3 << 3) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask0 << 20) | (lfriend_mask1 << 36) | (lfriend_mask2 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 12) | (lfriend_mask3 << 4) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask0 << 21) | (lfriend_mask1 << 37) | (lfriend_mask2 << 53) | (lfriend_mask5 << 20)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 11) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask0 << 22) | (lfriend_mask1 << 38) | (lfriend_mask2 << 54) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 10) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask0 << 23) | (lfriend_mask1 << 39) | (lfriend_mask2 << 55) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 9) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask0 << 24) | (lfriend_mask1 << 40) | (lfriend_mask2 << 56) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 8) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask0 << 25) | (lfriend_mask1 << 41) | (lfriend_mask2 << 57) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 7) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask0 << 26) | (lfriend_mask1 << 42) | (lfriend_mask2 << 58) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 6) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask0 << 27) | (lfriend_mask1 << 43) | (lfriend_mask2 << 59) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 5) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask0 << 28) | (lfriend_mask1 << 44) | (lfriend_mask2 << 60) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 4) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask0 << 29) | (lfriend_mask1 << 45) | (lfriend_mask2 << 61) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 3) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask0 << 30) | (lfriend_mask1 << 46) | (lfriend_mask2 << 62) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 2) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask0 << 31) | (lfriend_mask1 << 47) | (lfriend_mask2 << 63) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 1) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask0 << 32) | (lfriend_mask1 << 48) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask0 << 33) | (lfriend_mask1 << 49) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 15) | (lfriend_mask2 << 1) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask0 << 34) | (lfriend_mask1 << 50) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 14) | (lfriend_mask2 << 2) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask0 << 35) | (lfriend_mask1 << 51) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 13) | (lfriend_mask2 << 3) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask0 << 36) | (lfriend_mask1 << 52) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 12) | (lfriend_mask2 << 4) | (lfriend_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask0 << 37) | (lfriend_mask1 << 53) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 11) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask0 << 38) | (lfriend_mask1 << 54) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21) | (lfriend_mask5 << 37)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 10) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask0 << 39) | (lfriend_mask1 << 55) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22) | (lfriend_mask5 << 38)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 9) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask0 << 40) | (lfriend_mask1 << 56) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23) | (lfriend_mask5 << 39)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 8) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask0 << 41) | (lfriend_mask1 << 57) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24) | (lfriend_mask5 << 40)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 7) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask0 << 42) | (lfriend_mask1 << 58) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25) | (lfriend_mask5 << 41)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 6) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask0 << 43) | (lfriend_mask1 << 59) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26) | (lfriend_mask5 << 42)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 5) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask0 << 44) | (lfriend_mask1 << 60) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27) | (lfriend_mask5 << 43)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 4) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask0 << 45) | (lfriend_mask1 << 61) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28) | (lfriend_mask5 << 44)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 3) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask0 << 46) | (lfriend_mask1 << 62) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29) | (lfriend_mask5 << 45)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 2) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask0 << 47) | (lfriend_mask1 << 63) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30) | (lfriend_mask5 << 46)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 1) | (lfriend_mask2 << 15) | (lfriend_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask0 << 48) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31) | (lfriend_mask5 << 47)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1) | (lfriend_mask2 << 16) | (lfriend_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask0 << 49) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32) | (lfriend_mask5 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 15) | (lfriend_mask1 << 1) | (lfriend_mask2 << 17) | (lfriend_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask0 << 50) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33) | (lfriend_mask5 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 14) | (lfriend_mask1 << 2) | (lfriend_mask2 << 18) | (lfriend_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask0 << 51) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34) | (lfriend_mask5 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 13) | (lfriend_mask1 << 3) | (lfriend_mask2 << 19) | (lfriend_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask0 << 52) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35) | (lfriend_mask5 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 12) | (lfriend_mask1 << 4) | (lfriend_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask0 << 53) | (lfriend_mask3 << 20) | (lfriend_mask4 << 36) | (lfriend_mask5 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 11) | (lfriend_mask1 << 5) | (lfriend_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask0 << 54) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21) | (lfriend_mask4 << 37) | (lfriend_mask5 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 10) | (lfriend_mask1 << 6) | (lfriend_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask0 << 55) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22) | (lfriend_mask4 << 38) | (lfriend_mask5 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 9) | (lfriend_mask1 << 7) | (lfriend_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask0 << 56) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23) | (lfriend_mask4 << 39) | (lfriend_mask5 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 8) | (lfriend_mask1 << 8) | (lfriend_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask0 << 57) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24) | (lfriend_mask4 << 40) | (lfriend_mask5 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 7) | (lfriend_mask1 << 9) | (lfriend_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask0 << 58) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25) | (lfriend_mask4 << 41) | (lfriend_mask5 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 6) | (lfriend_mask1 << 10) | (lfriend_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask0 << 59) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26) | (lfriend_mask4 << 42) | (lfriend_mask5 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 5) | (lfriend_mask1 << 11) | (lfriend_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask0 << 60) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27) | (lfriend_mask4 << 43) | (lfriend_mask5 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 4) | (lfriend_mask1 << 12) | (lfriend_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask0 << 61) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28) | (lfriend_mask4 << 44) | (lfriend_mask5 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 3) | (lfriend_mask1 << 13) | (lfriend_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask0 << 62) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29) | (lfriend_mask4 << 45) | (lfriend_mask5 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 2) | (lfriend_mask1 << 14) | (lfriend_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask0 << 63) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30) | (lfriend_mask4 << 46) | (lfriend_mask5 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 1) | (lfriend_mask1 << 15) | (lfriend_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask2 << 15) | (lfriend_mask3 << 31) | (lfriend_mask4 << 47) | (lfriend_mask5 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0) | (lfriend_mask1 << 16) | (lfriend_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask2 << 16) | (lfriend_mask3 << 32) | (lfriend_mask4 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 1) | (lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask5)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask2 << 17) | (lfriend_mask3 << 33) | (lfriend_mask4 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 2) | (lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask4 >>> 15) | (lfriend_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask2 << 18) | (lfriend_mask3 << 34) | (lfriend_mask4 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 3) | (lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask4 >>> 14) | (lfriend_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask2 << 19) | (lfriend_mask3 << 35) | (lfriend_mask4 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 4) | (lfriend_mask1 << 20) | (lfriend_mask4 >>> 13) | (lfriend_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask2 << 20) | (lfriend_mask3 << 36) | (lfriend_mask4 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask4 >>> 12) | (lfriend_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask1 << 5) | (lfriend_mask2 << 21) | (lfriend_mask3 << 37) | (lfriend_mask4 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask4 >>> 11) | (lfriend_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask1 << 6) | (lfriend_mask2 << 22) | (lfriend_mask3 << 38) | (lfriend_mask4 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask4 >>> 10) | (lfriend_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask1 << 7) | (lfriend_mask2 << 23) | (lfriend_mask3 << 39) | (lfriend_mask4 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask4 >>> 9) | (lfriend_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask1 << 8) | (lfriend_mask2 << 24) | (lfriend_mask3 << 40) | (lfriend_mask4 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask4 >>> 8) | (lfriend_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask1 << 9) | (lfriend_mask2 << 25) | (lfriend_mask3 << 41) | (lfriend_mask4 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask4 >>> 7) | (lfriend_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 5:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask0 << 24) | (lfriend_mask1 << 40) | (lfriend_mask2 << 56) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 8) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask0 << 25) | (lfriend_mask1 << 41) | (lfriend_mask2 << 57) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 7) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask0 << 26) | (lfriend_mask1 << 42) | (lfriend_mask2 << 58) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 6) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask0 << 27) | (lfriend_mask1 << 43) | (lfriend_mask2 << 59) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 5) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask0 << 28) | (lfriend_mask1 << 44) | (lfriend_mask2 << 60) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 4) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask0 << 29) | (lfriend_mask1 << 45) | (lfriend_mask2 << 61) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 3) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask0 << 30) | (lfriend_mask1 << 46) | (lfriend_mask2 << 62) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 2) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask0 << 31) | (lfriend_mask1 << 47) | (lfriend_mask2 << 63) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 1) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask0 << 32) | (lfriend_mask1 << 48) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask0 << 33) | (lfriend_mask1 << 49) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 15) | (lfriend_mask2 << 1) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask0 << 34) | (lfriend_mask1 << 50) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 14) | (lfriend_mask2 << 2) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask0 << 35) | (lfriend_mask1 << 51) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 13) | (lfriend_mask2 << 3) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask0 << 36) | (lfriend_mask1 << 52) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 12) | (lfriend_mask2 << 4) | (lfriend_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask0 << 37) | (lfriend_mask1 << 53) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 11) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask0 << 38) | (lfriend_mask1 << 54) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21) | (lfriend_mask5 << 37)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 10) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask0 << 39) | (lfriend_mask1 << 55) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22) | (lfriend_mask5 << 38)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 9) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask0 << 40) | (lfriend_mask1 << 56) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23) | (lfriend_mask5 << 39)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 8) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask0 << 41) | (lfriend_mask1 << 57) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24) | (lfriend_mask5 << 40)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 7) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask0 << 42) | (lfriend_mask1 << 58) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25) | (lfriend_mask5 << 41)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 6) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask0 << 43) | (lfriend_mask1 << 59) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26) | (lfriend_mask5 << 42)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 5) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask0 << 44) | (lfriend_mask1 << 60) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27) | (lfriend_mask5 << 43)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 4) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask0 << 45) | (lfriend_mask1 << 61) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28) | (lfriend_mask5 << 44)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 3) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask0 << 46) | (lfriend_mask1 << 62) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29) | (lfriend_mask5 << 45)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 2) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask0 << 47) | (lfriend_mask1 << 63) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30) | (lfriend_mask5 << 46)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 1) | (lfriend_mask2 << 15) | (lfriend_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask0 << 48) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31) | (lfriend_mask5 << 47)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1) | (lfriend_mask2 << 16) | (lfriend_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask0 << 49) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32) | (lfriend_mask5 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 15) | (lfriend_mask1 << 1) | (lfriend_mask2 << 17) | (lfriend_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask0 << 50) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33) | (lfriend_mask5 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 14) | (lfriend_mask1 << 2) | (lfriend_mask2 << 18) | (lfriend_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask0 << 51) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34) | (lfriend_mask5 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 13) | (lfriend_mask1 << 3) | (lfriend_mask2 << 19) | (lfriend_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask0 << 52) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35) | (lfriend_mask5 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 12) | (lfriend_mask1 << 4) | (lfriend_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask0 << 53) | (lfriend_mask3 << 20) | (lfriend_mask4 << 36) | (lfriend_mask5 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 11) | (lfriend_mask1 << 5) | (lfriend_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask0 << 54) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21) | (lfriend_mask4 << 37) | (lfriend_mask5 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 10) | (lfriend_mask1 << 6) | (lfriend_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask0 << 55) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22) | (lfriend_mask4 << 38) | (lfriend_mask5 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 9) | (lfriend_mask1 << 7) | (lfriend_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask0 << 56) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23) | (lfriend_mask4 << 39) | (lfriend_mask5 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 8) | (lfriend_mask1 << 8) | (lfriend_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask0 << 57) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24) | (lfriend_mask4 << 40) | (lfriend_mask5 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 7) | (lfriend_mask1 << 9) | (lfriend_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask0 << 58) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25) | (lfriend_mask4 << 41) | (lfriend_mask5 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 6) | (lfriend_mask1 << 10) | (lfriend_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask0 << 59) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26) | (lfriend_mask4 << 42) | (lfriend_mask5 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 5) | (lfriend_mask1 << 11) | (lfriend_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask0 << 60) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27) | (lfriend_mask4 << 43) | (lfriend_mask5 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 4) | (lfriend_mask1 << 12) | (lfriend_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask0 << 61) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28) | (lfriend_mask4 << 44) | (lfriend_mask5 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 3) | (lfriend_mask1 << 13) | (lfriend_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask0 << 62) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29) | (lfriend_mask4 << 45) | (lfriend_mask5 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 2) | (lfriend_mask1 << 14) | (lfriend_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask0 << 63) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30) | (lfriend_mask4 << 46) | (lfriend_mask5 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 1) | (lfriend_mask1 << 15) | (lfriend_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask2 << 15) | (lfriend_mask3 << 31) | (lfriend_mask4 << 47) | (lfriend_mask5 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0) | (lfriend_mask1 << 16) | (lfriend_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask2 << 16) | (lfriend_mask3 << 32) | (lfriend_mask4 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 1) | (lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask5)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask2 << 17) | (lfriend_mask3 << 33) | (lfriend_mask4 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 2) | (lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask4 >>> 15) | (lfriend_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask2 << 18) | (lfriend_mask3 << 34) | (lfriend_mask4 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 3) | (lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask4 >>> 14) | (lfriend_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask2 << 19) | (lfriend_mask3 << 35) | (lfriend_mask4 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 4) | (lfriend_mask1 << 20) | (lfriend_mask4 >>> 13) | (lfriend_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask2 << 20) | (lfriend_mask3 << 36) | (lfriend_mask4 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask4 >>> 12) | (lfriend_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask1 << 5) | (lfriend_mask2 << 21) | (lfriend_mask3 << 37) | (lfriend_mask4 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask4 >>> 11) | (lfriend_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask1 << 6) | (lfriend_mask2 << 22) | (lfriend_mask3 << 38) | (lfriend_mask4 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask4 >>> 10) | (lfriend_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask1 << 7) | (lfriend_mask2 << 23) | (lfriend_mask3 << 39) | (lfriend_mask4 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask4 >>> 9) | (lfriend_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask1 << 8) | (lfriend_mask2 << 24) | (lfriend_mask3 << 40) | (lfriend_mask4 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask4 >>> 8) | (lfriend_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask1 << 9) | (lfriend_mask2 << 25) | (lfriend_mask3 << 41) | (lfriend_mask4 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask4 >>> 7) | (lfriend_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask1 << 10) | (lfriend_mask2 << 26) | (lfriend_mask3 << 42) | (lfriend_mask4 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask4 >>> 6) | (lfriend_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask1 << 11) | (lfriend_mask2 << 27) | (lfriend_mask3 << 43) | (lfriend_mask4 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask4 >>> 5) | (lfriend_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask1 << 12) | (lfriend_mask2 << 28) | (lfriend_mask3 << 44) | (lfriend_mask4 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask4 >>> 4) | (lfriend_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask1 << 13) | (lfriend_mask2 << 29) | (lfriend_mask3 << 45) | (lfriend_mask4 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask4 >>> 3) | (lfriend_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask1 << 14) | (lfriend_mask2 << 30) | (lfriend_mask3 << 46) | (lfriend_mask4 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask4 >>> 2) | (lfriend_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask1 << 15) | (lfriend_mask2 << 31) | (lfriend_mask3 << 47) | (lfriend_mask4 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask4 >>> 1) | (lfriend_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask1 << 16) | (lfriend_mask2 << 32) | (lfriend_mask3 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask4) | (lfriend_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask3 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask3 >>> 15) | (lfriend_mask4 << 1) | (lfriend_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask3 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask3 >>> 14) | (lfriend_mask4 << 2) | (lfriend_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 6:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask0 << 33) | (lfriend_mask1 << 49) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 15) | (lfriend_mask2 << 1) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask0 << 34) | (lfriend_mask1 << 50) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 14) | (lfriend_mask2 << 2) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask0 << 35) | (lfriend_mask1 << 51) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 13) | (lfriend_mask2 << 3) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask0 << 36) | (lfriend_mask1 << 52) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 12) | (lfriend_mask2 << 4) | (lfriend_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask0 << 37) | (lfriend_mask1 << 53) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 11) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask0 << 38) | (lfriend_mask1 << 54) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21) | (lfriend_mask5 << 37)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 10) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask0 << 39) | (lfriend_mask1 << 55) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22) | (lfriend_mask5 << 38)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 9) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask0 << 40) | (lfriend_mask1 << 56) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23) | (lfriend_mask5 << 39)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 8) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask0 << 41) | (lfriend_mask1 << 57) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24) | (lfriend_mask5 << 40)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 7) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask0 << 42) | (lfriend_mask1 << 58) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25) | (lfriend_mask5 << 41)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 6) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask0 << 43) | (lfriend_mask1 << 59) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26) | (lfriend_mask5 << 42)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 5) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask0 << 44) | (lfriend_mask1 << 60) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27) | (lfriend_mask5 << 43)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 4) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask0 << 45) | (lfriend_mask1 << 61) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28) | (lfriend_mask5 << 44)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 3) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask0 << 46) | (lfriend_mask1 << 62) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29) | (lfriend_mask5 << 45)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 2) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask0 << 47) | (lfriend_mask1 << 63) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30) | (lfriend_mask5 << 46)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 1) | (lfriend_mask2 << 15) | (lfriend_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask0 << 48) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31) | (lfriend_mask5 << 47)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1) | (lfriend_mask2 << 16) | (lfriend_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask0 << 49) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32) | (lfriend_mask5 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 15) | (lfriend_mask1 << 1) | (lfriend_mask2 << 17) | (lfriend_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask0 << 50) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33) | (lfriend_mask5 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 14) | (lfriend_mask1 << 2) | (lfriend_mask2 << 18) | (lfriend_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask0 << 51) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34) | (lfriend_mask5 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 13) | (lfriend_mask1 << 3) | (lfriend_mask2 << 19) | (lfriend_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask0 << 52) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35) | (lfriend_mask5 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 12) | (lfriend_mask1 << 4) | (lfriend_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask0 << 53) | (lfriend_mask3 << 20) | (lfriend_mask4 << 36) | (lfriend_mask5 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 11) | (lfriend_mask1 << 5) | (lfriend_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask0 << 54) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21) | (lfriend_mask4 << 37) | (lfriend_mask5 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 10) | (lfriend_mask1 << 6) | (lfriend_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask0 << 55) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22) | (lfriend_mask4 << 38) | (lfriend_mask5 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 9) | (lfriend_mask1 << 7) | (lfriend_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask0 << 56) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23) | (lfriend_mask4 << 39) | (lfriend_mask5 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 8) | (lfriend_mask1 << 8) | (lfriend_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask0 << 57) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24) | (lfriend_mask4 << 40) | (lfriend_mask5 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 7) | (lfriend_mask1 << 9) | (lfriend_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask0 << 58) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25) | (lfriend_mask4 << 41) | (lfriend_mask5 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 6) | (lfriend_mask1 << 10) | (lfriend_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask0 << 59) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26) | (lfriend_mask4 << 42) | (lfriend_mask5 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 5) | (lfriend_mask1 << 11) | (lfriend_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask0 << 60) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27) | (lfriend_mask4 << 43) | (lfriend_mask5 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 4) | (lfriend_mask1 << 12) | (lfriend_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask0 << 61) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28) | (lfriend_mask4 << 44) | (lfriend_mask5 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 3) | (lfriend_mask1 << 13) | (lfriend_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask0 << 62) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29) | (lfriend_mask4 << 45) | (lfriend_mask5 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 2) | (lfriend_mask1 << 14) | (lfriend_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask0 << 63) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30) | (lfriend_mask4 << 46) | (lfriend_mask5 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 1) | (lfriend_mask1 << 15) | (lfriend_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask2 << 15) | (lfriend_mask3 << 31) | (lfriend_mask4 << 47) | (lfriend_mask5 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0) | (lfriend_mask1 << 16) | (lfriend_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask2 << 16) | (lfriend_mask3 << 32) | (lfriend_mask4 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 1) | (lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask5)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask2 << 17) | (lfriend_mask3 << 33) | (lfriend_mask4 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 2) | (lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask4 >>> 15) | (lfriend_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask2 << 18) | (lfriend_mask3 << 34) | (lfriend_mask4 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 3) | (lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask4 >>> 14) | (lfriend_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask2 << 19) | (lfriend_mask3 << 35) | (lfriend_mask4 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 4) | (lfriend_mask1 << 20) | (lfriend_mask4 >>> 13) | (lfriend_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask2 << 20) | (lfriend_mask3 << 36) | (lfriend_mask4 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask4 >>> 12) | (lfriend_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask1 << 5) | (lfriend_mask2 << 21) | (lfriend_mask3 << 37) | (lfriend_mask4 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask4 >>> 11) | (lfriend_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask1 << 6) | (lfriend_mask2 << 22) | (lfriend_mask3 << 38) | (lfriend_mask4 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask4 >>> 10) | (lfriend_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask1 << 7) | (lfriend_mask2 << 23) | (lfriend_mask3 << 39) | (lfriend_mask4 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask4 >>> 9) | (lfriend_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask1 << 8) | (lfriend_mask2 << 24) | (lfriend_mask3 << 40) | (lfriend_mask4 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask4 >>> 8) | (lfriend_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask1 << 9) | (lfriend_mask2 << 25) | (lfriend_mask3 << 41) | (lfriend_mask4 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask4 >>> 7) | (lfriend_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask1 << 10) | (lfriend_mask2 << 26) | (lfriend_mask3 << 42) | (lfriend_mask4 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask4 >>> 6) | (lfriend_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask1 << 11) | (lfriend_mask2 << 27) | (lfriend_mask3 << 43) | (lfriend_mask4 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask4 >>> 5) | (lfriend_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask1 << 12) | (lfriend_mask2 << 28) | (lfriend_mask3 << 44) | (lfriend_mask4 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask4 >>> 4) | (lfriend_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask1 << 13) | (lfriend_mask2 << 29) | (lfriend_mask3 << 45) | (lfriend_mask4 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask4 >>> 3) | (lfriend_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask1 << 14) | (lfriend_mask2 << 30) | (lfriend_mask3 << 46) | (lfriend_mask4 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask4 >>> 2) | (lfriend_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask1 << 15) | (lfriend_mask2 << 31) | (lfriend_mask3 << 47) | (lfriend_mask4 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask4 >>> 1) | (lfriend_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask1 << 16) | (lfriend_mask2 << 32) | (lfriend_mask3 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask4) | (lfriend_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask3 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask3 >>> 15) | (lfriend_mask4 << 1) | (lfriend_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask3 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask3 >>> 14) | (lfriend_mask4 << 2) | (lfriend_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask3 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 20) | (lfriend_mask3 >>> 13) | (lfriend_mask4 << 3) | (lfriend_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask1 << 20) | (lfriend_mask2 << 36) | (lfriend_mask3 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 21) | (lfriend_mask3 >>> 12) | (lfriend_mask4 << 4) | (lfriend_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask2 << 37) | (lfriend_mask3 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 22) | (lfriend_mask3 >>> 11) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask2 << 38) | (lfriend_mask3 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 23) | (lfriend_mask3 >>> 10) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask2 << 39) | (lfriend_mask3 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 24) | (lfriend_mask3 >>> 9) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask2 << 40) | (lfriend_mask3 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 25) | (lfriend_mask3 >>> 8) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask2 << 41) | (lfriend_mask3 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 26) | (lfriend_mask3 >>> 7) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask2 << 42) | (lfriend_mask3 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 27) | (lfriend_mask3 >>> 6) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask2 << 43) | (lfriend_mask3 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 28) | (lfriend_mask3 >>> 5) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 7:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask0 << 42) | (lfriend_mask1 << 58) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25) | (lfriend_mask5 << 41)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 6) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask0 << 43) | (lfriend_mask1 << 59) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26) | (lfriend_mask5 << 42)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 5) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask0 << 44) | (lfriend_mask1 << 60) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27) | (lfriend_mask5 << 43)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 4) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask0 << 45) | (lfriend_mask1 << 61) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28) | (lfriend_mask5 << 44)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 3) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask0 << 46) | (lfriend_mask1 << 62) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29) | (lfriend_mask5 << 45)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 2) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask0 << 47) | (lfriend_mask1 << 63) | (lfriend_mask3 << 14) | (lfriend_mask4 << 30) | (lfriend_mask5 << 46)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1 >>> 1) | (lfriend_mask2 << 15) | (lfriend_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask0 << 48) | (lfriend_mask3 << 15) | (lfriend_mask4 << 31) | (lfriend_mask5 << 47)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask1) | (lfriend_mask2 << 16) | (lfriend_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask0 << 49) | (lfriend_mask3 << 16) | (lfriend_mask4 << 32) | (lfriend_mask5 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 15) | (lfriend_mask1 << 1) | (lfriend_mask2 << 17) | (lfriend_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask0 << 50) | (lfriend_mask3 << 17) | (lfriend_mask4 << 33) | (lfriend_mask5 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 14) | (lfriend_mask1 << 2) | (lfriend_mask2 << 18) | (lfriend_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask0 << 51) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34) | (lfriend_mask5 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 13) | (lfriend_mask1 << 3) | (lfriend_mask2 << 19) | (lfriend_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask0 << 52) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35) | (lfriend_mask5 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 12) | (lfriend_mask1 << 4) | (lfriend_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask0 << 53) | (lfriend_mask3 << 20) | (lfriend_mask4 << 36) | (lfriend_mask5 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 11) | (lfriend_mask1 << 5) | (lfriend_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask0 << 54) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21) | (lfriend_mask4 << 37) | (lfriend_mask5 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 10) | (lfriend_mask1 << 6) | (lfriend_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask0 << 55) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22) | (lfriend_mask4 << 38) | (lfriend_mask5 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 9) | (lfriend_mask1 << 7) | (lfriend_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask0 << 56) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23) | (lfriend_mask4 << 39) | (lfriend_mask5 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 8) | (lfriend_mask1 << 8) | (lfriend_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask0 << 57) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24) | (lfriend_mask4 << 40) | (lfriend_mask5 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 7) | (lfriend_mask1 << 9) | (lfriend_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask0 << 58) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25) | (lfriend_mask4 << 41) | (lfriend_mask5 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 6) | (lfriend_mask1 << 10) | (lfriend_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask0 << 59) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26) | (lfriend_mask4 << 42) | (lfriend_mask5 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 5) | (lfriend_mask1 << 11) | (lfriend_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask0 << 60) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27) | (lfriend_mask4 << 43) | (lfriend_mask5 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 4) | (lfriend_mask1 << 12) | (lfriend_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask0 << 61) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28) | (lfriend_mask4 << 44) | (lfriend_mask5 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 3) | (lfriend_mask1 << 13) | (lfriend_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask0 << 62) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29) | (lfriend_mask4 << 45) | (lfriend_mask5 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 2) | (lfriend_mask1 << 14) | (lfriend_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask0 << 63) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30) | (lfriend_mask4 << 46) | (lfriend_mask5 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 1) | (lfriend_mask1 << 15) | (lfriend_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask2 << 15) | (lfriend_mask3 << 31) | (lfriend_mask4 << 47) | (lfriend_mask5 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0) | (lfriend_mask1 << 16) | (lfriend_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask2 << 16) | (lfriend_mask3 << 32) | (lfriend_mask4 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 1) | (lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask5)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask2 << 17) | (lfriend_mask3 << 33) | (lfriend_mask4 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 2) | (lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask4 >>> 15) | (lfriend_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask2 << 18) | (lfriend_mask3 << 34) | (lfriend_mask4 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 3) | (lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask4 >>> 14) | (lfriend_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask2 << 19) | (lfriend_mask3 << 35) | (lfriend_mask4 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 4) | (lfriend_mask1 << 20) | (lfriend_mask4 >>> 13) | (lfriend_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask2 << 20) | (lfriend_mask3 << 36) | (lfriend_mask4 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask4 >>> 12) | (lfriend_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask1 << 5) | (lfriend_mask2 << 21) | (lfriend_mask3 << 37) | (lfriend_mask4 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask4 >>> 11) | (lfriend_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask1 << 6) | (lfriend_mask2 << 22) | (lfriend_mask3 << 38) | (lfriend_mask4 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask4 >>> 10) | (lfriend_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask1 << 7) | (lfriend_mask2 << 23) | (lfriend_mask3 << 39) | (lfriend_mask4 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask4 >>> 9) | (lfriend_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask1 << 8) | (lfriend_mask2 << 24) | (lfriend_mask3 << 40) | (lfriend_mask4 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask4 >>> 8) | (lfriend_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask1 << 9) | (lfriend_mask2 << 25) | (lfriend_mask3 << 41) | (lfriend_mask4 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask4 >>> 7) | (lfriend_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask1 << 10) | (lfriend_mask2 << 26) | (lfriend_mask3 << 42) | (lfriend_mask4 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask4 >>> 6) | (lfriend_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask1 << 11) | (lfriend_mask2 << 27) | (lfriend_mask3 << 43) | (lfriend_mask4 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask4 >>> 5) | (lfriend_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask1 << 12) | (lfriend_mask2 << 28) | (lfriend_mask3 << 44) | (lfriend_mask4 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask4 >>> 4) | (lfriend_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask1 << 13) | (lfriend_mask2 << 29) | (lfriend_mask3 << 45) | (lfriend_mask4 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask4 >>> 3) | (lfriend_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask1 << 14) | (lfriend_mask2 << 30) | (lfriend_mask3 << 46) | (lfriend_mask4 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask4 >>> 2) | (lfriend_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask1 << 15) | (lfriend_mask2 << 31) | (lfriend_mask3 << 47) | (lfriend_mask4 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask4 >>> 1) | (lfriend_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask1 << 16) | (lfriend_mask2 << 32) | (lfriend_mask3 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask4) | (lfriend_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask3 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask3 >>> 15) | (lfriend_mask4 << 1) | (lfriend_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask3 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask3 >>> 14) | (lfriend_mask4 << 2) | (lfriend_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask3 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 20) | (lfriend_mask3 >>> 13) | (lfriend_mask4 << 3) | (lfriend_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask1 << 20) | (lfriend_mask2 << 36) | (lfriend_mask3 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 21) | (lfriend_mask3 >>> 12) | (lfriend_mask4 << 4) | (lfriend_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask2 << 37) | (lfriend_mask3 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 22) | (lfriend_mask3 >>> 11) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask2 << 38) | (lfriend_mask3 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 23) | (lfriend_mask3 >>> 10) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask2 << 39) | (lfriend_mask3 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 24) | (lfriend_mask3 >>> 9) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask2 << 40) | (lfriend_mask3 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 25) | (lfriend_mask3 >>> 8) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask2 << 41) | (lfriend_mask3 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 26) | (lfriend_mask3 >>> 7) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask2 << 42) | (lfriend_mask3 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 27) | (lfriend_mask3 >>> 6) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask2 << 43) | (lfriend_mask3 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 28) | (lfriend_mask3 >>> 5) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask2 << 44) | (lfriend_mask3 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 29) | (lfriend_mask3 >>> 4) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask2 << 45) | (lfriend_mask3 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 30) | (lfriend_mask3 >>> 3) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask2 << 46) | (lfriend_mask3 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 31) | (lfriend_mask3 >>> 2) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask2 << 47) | (lfriend_mask3 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 32) | (lfriend_mask3 >>> 1) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask2 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 33) | (lfriend_mask3) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask2 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 34) | (lfriend_mask2 >>> 15) | (lfriend_mask3 << 1) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask2 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 35) | (lfriend_mask2 >>> 14) | (lfriend_mask3 << 2) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask2 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 13) | (lfriend_mask3 << 3) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask0 << 20) | (lfriend_mask1 << 36) | (lfriend_mask2 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 12) | (lfriend_mask3 << 4) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 8:
                switch (myloc.x) {
                    case 0: 
                        friend_mask[0] = ((lfriend_mask0 << 51) | (lfriend_mask3 << 18) | (lfriend_mask4 << 34) | (lfriend_mask5 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 13) | (lfriend_mask1 << 3) | (lfriend_mask2 << 19) | (lfriend_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        friend_mask[0] = ((lfriend_mask0 << 52) | (lfriend_mask3 << 19) | (lfriend_mask4 << 35) | (lfriend_mask5 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 12) | (lfriend_mask1 << 4) | (lfriend_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        friend_mask[0] = ((lfriend_mask0 << 53) | (lfriend_mask3 << 20) | (lfriend_mask4 << 36) | (lfriend_mask5 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 11) | (lfriend_mask1 << 5) | (lfriend_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        friend_mask[0] = ((lfriend_mask0 << 54) | (lfriend_mask2 << 5) | (lfriend_mask3 << 21) | (lfriend_mask4 << 37) | (lfriend_mask5 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 10) | (lfriend_mask1 << 6) | (lfriend_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        friend_mask[0] = ((lfriend_mask0 << 55) | (lfriend_mask2 << 6) | (lfriend_mask3 << 22) | (lfriend_mask4 << 38) | (lfriend_mask5 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 9) | (lfriend_mask1 << 7) | (lfriend_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        friend_mask[0] = ((lfriend_mask0 << 56) | (lfriend_mask2 << 7) | (lfriend_mask3 << 23) | (lfriend_mask4 << 39) | (lfriend_mask5 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 8) | (lfriend_mask1 << 8) | (lfriend_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        friend_mask[0] = ((lfriend_mask0 << 57) | (lfriend_mask2 << 8) | (lfriend_mask3 << 24) | (lfriend_mask4 << 40) | (lfriend_mask5 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 7) | (lfriend_mask1 << 9) | (lfriend_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        friend_mask[0] = ((lfriend_mask0 << 58) | (lfriend_mask2 << 9) | (lfriend_mask3 << 25) | (lfriend_mask4 << 41) | (lfriend_mask5 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 6) | (lfriend_mask1 << 10) | (lfriend_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        friend_mask[0] = ((lfriend_mask0 << 59) | (lfriend_mask2 << 10) | (lfriend_mask3 << 26) | (lfriend_mask4 << 42) | (lfriend_mask5 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 5) | (lfriend_mask1 << 11) | (lfriend_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        friend_mask[0] = ((lfriend_mask0 << 60) | (lfriend_mask2 << 11) | (lfriend_mask3 << 27) | (lfriend_mask4 << 43) | (lfriend_mask5 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 4) | (lfriend_mask1 << 12) | (lfriend_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        friend_mask[0] = ((lfriend_mask0 << 61) | (lfriend_mask2 << 12) | (lfriend_mask3 << 28) | (lfriend_mask4 << 44) | (lfriend_mask5 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 3) | (lfriend_mask1 << 13) | (lfriend_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        friend_mask[0] = ((lfriend_mask0 << 62) | (lfriend_mask2 << 13) | (lfriend_mask3 << 29) | (lfriend_mask4 << 45) | (lfriend_mask5 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 2) | (lfriend_mask1 << 14) | (lfriend_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        friend_mask[0] = ((lfriend_mask0 << 63) | (lfriend_mask2 << 14) | (lfriend_mask3 << 30) | (lfriend_mask4 << 46) | (lfriend_mask5 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 >>> 1) | (lfriend_mask1 << 15) | (lfriend_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        friend_mask[0] = ((lfriend_mask2 << 15) | (lfriend_mask3 << 31) | (lfriend_mask4 << 47) | (lfriend_mask5 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0) | (lfriend_mask1 << 16) | (lfriend_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        friend_mask[0] = ((lfriend_mask2 << 16) | (lfriend_mask3 << 32) | (lfriend_mask4 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 1) | (lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask5)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        friend_mask[0] = ((lfriend_mask2 << 17) | (lfriend_mask3 << 33) | (lfriend_mask4 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 2) | (lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask4 >>> 15) | (lfriend_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        friend_mask[0] = ((lfriend_mask2 << 18) | (lfriend_mask3 << 34) | (lfriend_mask4 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 3) | (lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask4 >>> 14) | (lfriend_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        friend_mask[0] = ((lfriend_mask2 << 19) | (lfriend_mask3 << 35) | (lfriend_mask4 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 4) | (lfriend_mask1 << 20) | (lfriend_mask4 >>> 13) | (lfriend_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        friend_mask[0] = ((lfriend_mask2 << 20) | (lfriend_mask3 << 36) | (lfriend_mask4 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask4 >>> 12) | (lfriend_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        friend_mask[0] = ((lfriend_mask1 << 5) | (lfriend_mask2 << 21) | (lfriend_mask3 << 37) | (lfriend_mask4 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask4 >>> 11) | (lfriend_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        friend_mask[0] = ((lfriend_mask1 << 6) | (lfriend_mask2 << 22) | (lfriend_mask3 << 38) | (lfriend_mask4 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask4 >>> 10) | (lfriend_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        friend_mask[0] = ((lfriend_mask1 << 7) | (lfriend_mask2 << 23) | (lfriend_mask3 << 39) | (lfriend_mask4 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask4 >>> 9) | (lfriend_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        friend_mask[0] = ((lfriend_mask1 << 8) | (lfriend_mask2 << 24) | (lfriend_mask3 << 40) | (lfriend_mask4 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask4 >>> 8) | (lfriend_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        friend_mask[0] = ((lfriend_mask1 << 9) | (lfriend_mask2 << 25) | (lfriend_mask3 << 41) | (lfriend_mask4 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask4 >>> 7) | (lfriend_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        friend_mask[0] = ((lfriend_mask1 << 10) | (lfriend_mask2 << 26) | (lfriend_mask3 << 42) | (lfriend_mask4 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask4 >>> 6) | (lfriend_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        friend_mask[0] = ((lfriend_mask1 << 11) | (lfriend_mask2 << 27) | (lfriend_mask3 << 43) | (lfriend_mask4 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask4 >>> 5) | (lfriend_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        friend_mask[0] = ((lfriend_mask1 << 12) | (lfriend_mask2 << 28) | (lfriend_mask3 << 44) | (lfriend_mask4 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask4 >>> 4) | (lfriend_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        friend_mask[0] = ((lfriend_mask1 << 13) | (lfriend_mask2 << 29) | (lfriend_mask3 << 45) | (lfriend_mask4 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask4 >>> 3) | (lfriend_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        friend_mask[0] = ((lfriend_mask1 << 14) | (lfriend_mask2 << 30) | (lfriend_mask3 << 46) | (lfriend_mask4 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask4 >>> 2) | (lfriend_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        friend_mask[0] = ((lfriend_mask1 << 15) | (lfriend_mask2 << 31) | (lfriend_mask3 << 47) | (lfriend_mask4 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask4 >>> 1) | (lfriend_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        friend_mask[0] = ((lfriend_mask1 << 16) | (lfriend_mask2 << 32) | (lfriend_mask3 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask4) | (lfriend_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        friend_mask[0] = ((lfriend_mask1 << 17) | (lfriend_mask2 << 33) | (lfriend_mask3 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask3 >>> 15) | (lfriend_mask4 << 1) | (lfriend_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        friend_mask[0] = ((lfriend_mask1 << 18) | (lfriend_mask2 << 34) | (lfriend_mask3 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask3 >>> 14) | (lfriend_mask4 << 2) | (lfriend_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        friend_mask[0] = ((lfriend_mask1 << 19) | (lfriend_mask2 << 35) | (lfriend_mask3 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 20) | (lfriend_mask3 >>> 13) | (lfriend_mask4 << 3) | (lfriend_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        friend_mask[0] = ((lfriend_mask1 << 20) | (lfriend_mask2 << 36) | (lfriend_mask3 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 21) | (lfriend_mask3 >>> 12) | (lfriend_mask4 << 4) | (lfriend_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        friend_mask[0] = ((lfriend_mask0 << 5) | (lfriend_mask1 << 21) | (lfriend_mask2 << 37) | (lfriend_mask3 << 53)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 22) | (lfriend_mask3 >>> 11) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        friend_mask[0] = ((lfriend_mask0 << 6) | (lfriend_mask1 << 22) | (lfriend_mask2 << 38) | (lfriend_mask3 << 54)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 23) | (lfriend_mask3 >>> 10) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        friend_mask[0] = ((lfriend_mask0 << 7) | (lfriend_mask1 << 23) | (lfriend_mask2 << 39) | (lfriend_mask3 << 55)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 24) | (lfriend_mask3 >>> 9) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        friend_mask[0] = ((lfriend_mask0 << 8) | (lfriend_mask1 << 24) | (lfriend_mask2 << 40) | (lfriend_mask3 << 56)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 25) | (lfriend_mask3 >>> 8) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        friend_mask[0] = ((lfriend_mask0 << 9) | (lfriend_mask1 << 25) | (lfriend_mask2 << 41) | (lfriend_mask3 << 57)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 26) | (lfriend_mask3 >>> 7) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        friend_mask[0] = ((lfriend_mask0 << 10) | (lfriend_mask1 << 26) | (lfriend_mask2 << 42) | (lfriend_mask3 << 58)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 27) | (lfriend_mask3 >>> 6) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        friend_mask[0] = ((lfriend_mask0 << 11) | (lfriend_mask1 << 27) | (lfriend_mask2 << 43) | (lfriend_mask3 << 59)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 28) | (lfriend_mask3 >>> 5) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        friend_mask[0] = ((lfriend_mask0 << 12) | (lfriend_mask1 << 28) | (lfriend_mask2 << 44) | (lfriend_mask3 << 60)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 29) | (lfriend_mask3 >>> 4) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        friend_mask[0] = ((lfriend_mask0 << 13) | (lfriend_mask1 << 29) | (lfriend_mask2 << 45) | (lfriend_mask3 << 61)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 30) | (lfriend_mask3 >>> 3) | (lfriend_mask4 << 13) | (lfriend_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        friend_mask[0] = ((lfriend_mask0 << 14) | (lfriend_mask1 << 30) | (lfriend_mask2 << 46) | (lfriend_mask3 << 62)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 31) | (lfriend_mask3 >>> 2) | (lfriend_mask4 << 14) | (lfriend_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        friend_mask[0] = ((lfriend_mask0 << 15) | (lfriend_mask1 << 31) | (lfriend_mask2 << 47) | (lfriend_mask3 << 63)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 32) | (lfriend_mask3 >>> 1) | (lfriend_mask4 << 15) | (lfriend_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        friend_mask[0] = ((lfriend_mask0 << 16) | (lfriend_mask1 << 32) | (lfriend_mask2 << 48)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 33) | (lfriend_mask3) | (lfriend_mask4 << 16) | (lfriend_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        friend_mask[0] = ((lfriend_mask0 << 17) | (lfriend_mask1 << 33) | (lfriend_mask2 << 49)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 34) | (lfriend_mask2 >>> 15) | (lfriend_mask3 << 1) | (lfriend_mask4 << 17) | (lfriend_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        friend_mask[0] = ((lfriend_mask0 << 18) | (lfriend_mask1 << 34) | (lfriend_mask2 << 50)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask0 << 35) | (lfriend_mask2 >>> 14) | (lfriend_mask3 << 2) | (lfriend_mask4 << 18) | (lfriend_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        friend_mask[0] = ((lfriend_mask0 << 19) | (lfriend_mask1 << 35) | (lfriend_mask2 << 51)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 13) | (lfriend_mask3 << 3) | (lfriend_mask4 << 19) | (lfriend_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        friend_mask[0] = ((lfriend_mask0 << 20) | (lfriend_mask1 << 36) | (lfriend_mask2 << 52)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 12) | (lfriend_mask3 << 4) | (lfriend_mask4 << 20) | (lfriend_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        friend_mask[0] = ((lfriend_mask0 << 21) | (lfriend_mask1 << 37) | (lfriend_mask2 << 53) | (lfriend_mask5 << 20)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 11) | (lfriend_mask3 << 5) | (lfriend_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        friend_mask[0] = ((lfriend_mask0 << 22) | (lfriend_mask1 << 38) | (lfriend_mask2 << 54) | (lfriend_mask4 << 5) | (lfriend_mask5 << 21)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 10) | (lfriend_mask3 << 6) | (lfriend_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        friend_mask[0] = ((lfriend_mask0 << 23) | (lfriend_mask1 << 39) | (lfriend_mask2 << 55) | (lfriend_mask4 << 6) | (lfriend_mask5 << 22)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 9) | (lfriend_mask3 << 7) | (lfriend_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        friend_mask[0] = ((lfriend_mask0 << 24) | (lfriend_mask1 << 40) | (lfriend_mask2 << 56) | (lfriend_mask4 << 7) | (lfriend_mask5 << 23)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 8) | (lfriend_mask3 << 8) | (lfriend_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        friend_mask[0] = ((lfriend_mask0 << 25) | (lfriend_mask1 << 41) | (lfriend_mask2 << 57) | (lfriend_mask4 << 8) | (lfriend_mask5 << 24)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 7) | (lfriend_mask3 << 9) | (lfriend_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        friend_mask[0] = ((lfriend_mask0 << 26) | (lfriend_mask1 << 42) | (lfriend_mask2 << 58) | (lfriend_mask4 << 9) | (lfriend_mask5 << 25)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 6) | (lfriend_mask3 << 10) | (lfriend_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        friend_mask[0] = ((lfriend_mask0 << 27) | (lfriend_mask1 << 43) | (lfriend_mask2 << 59) | (lfriend_mask4 << 10) | (lfriend_mask5 << 26)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 5) | (lfriend_mask3 << 11) | (lfriend_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        friend_mask[0] = ((lfriend_mask0 << 28) | (lfriend_mask1 << 44) | (lfriend_mask2 << 60) | (lfriend_mask4 << 11) | (lfriend_mask5 << 27)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 4) | (lfriend_mask3 << 12) | (lfriend_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        friend_mask[0] = ((lfriend_mask0 << 29) | (lfriend_mask1 << 45) | (lfriend_mask2 << 61) | (lfriend_mask4 << 12) | (lfriend_mask5 << 28)) & 0xfffffffffff80000L;
                        friend_mask[1] = ((lfriend_mask2 >>> 3) | (lfriend_mask3 << 13) | (lfriend_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            default: 
        }
    }

    void load_enemies() throws GameActionException {
        int cc = 0;
        int tenemy_mask0 = 0;
        int tenemy_mask1 = 0;
        int tenemy_mask2 = 0;
        int tenemy_mask3 = 0;
        int tenemy_mask4 = 0;
        int tenemy_mask5 = 0;
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        for (int j = enemies.length; j-- > 0; ) {
            RobotInfo r = enemies[j];
            switch (r.type) {
                case LAUNCHER:
                switch (r.location.hashCode()) {
                    case 32768: case 3571715: case 2981892: case 2392069: case 1802246: case 1212423: 
                    case 622600: case 32777: case 3571724: case 2981901: case 2392078: case 1802255: 
                    case 1212432: case 622609: case 32786: case 3571733: case 2981910: case 2392087: 
                    case 1802264: case 1212441: case 622618: case 32795: case 3571742: case 2981919: 
                    case 2392096: case 1802273: case 1212450: case 622627: case 32804: case 3571751: 
                    case 2981928: case 2392105: case 1802282: case 1212459: case 622636: case 32813: 
                    case 3571760: case 2981937: case 2392114: case 1802291: case 1212468: case 622645: 
                    case 32822: case 3571769: case 2981946: case 2392123: 
                        tenemy_mask0 += 1; continue;
                    case 98304: case 3637251: case 3047428: case 2457605: case 1867782: case 1277959: 
                    case 688136: case 98313: case 3637260: case 3047437: case 2457614: case 1867791: 
                    case 1277968: case 688145: case 98322: case 3637269: case 3047446: case 2457623: 
                    case 1867800: case 1277977: case 688154: case 98331: case 3637278: case 3047455: 
                    case 2457632: case 1867809: case 1277986: case 688163: case 98340: case 3637287: 
                    case 3047464: case 2457641: case 1867818: case 1277995: case 688172: case 98349: 
                    case 3637296: case 3047473: case 2457650: case 1867827: case 1278004: case 688181: 
                    case 98358: case 3637305: case 3047482: case 2457659: 
                        tenemy_mask0 += 2; continue;
                    case 163840: case 3702787: case 3112964: case 2523141: case 1933318: case 1343495: 
                    case 753672: case 163849: case 3702796: case 3112973: case 2523150: case 1933327: 
                    case 1343504: case 753681: case 163858: case 3702805: case 3112982: case 2523159: 
                    case 1933336: case 1343513: case 753690: case 163867: case 3702814: case 3112991: 
                    case 2523168: case 1933345: case 1343522: case 753699: case 163876: case 3702823: 
                    case 3113000: case 2523177: case 1933354: case 1343531: case 753708: case 163885: 
                    case 3702832: case 3113009: case 2523186: case 1933363: case 1343540: case 753717: 
                    case 163894: case 3702841: case 3113018: case 2523195: 
                        tenemy_mask0 += 4; continue;
                    case 229376: case 3768323: case 3178500: case 2588677: case 1998854: case 1409031: 
                    case 819208: case 229385: case 3768332: case 3178509: case 2588686: case 1998863: 
                    case 1409040: case 819217: case 229394: case 3768341: case 3178518: case 2588695: 
                    case 1998872: case 1409049: case 819226: case 229403: case 3768350: case 3178527: 
                    case 2588704: case 1998881: case 1409058: case 819235: case 229412: case 3768359: 
                    case 3178536: case 2588713: case 1998890: case 1409067: case 819244: case 229421: 
                    case 3768368: case 3178545: case 2588722: case 1998899: case 1409076: case 819253: 
                    case 229430: case 3768377: case 3178554: case 2588731: 
                        tenemy_mask0 += 8; continue;
                    case 294912: case 3833859: case 3244036: case 2654213: case 2064390: case 1474567: 
                    case 884744: case 294921: case 3833868: case 3244045: case 2654222: case 2064399: 
                    case 1474576: case 884753: case 294930: case 3833877: case 3244054: case 2654231: 
                    case 2064408: case 1474585: case 884762: case 294939: case 3833886: case 3244063: 
                    case 2654240: case 2064417: case 1474594: case 884771: case 294948: case 3833895: 
                    case 3244072: case 2654249: case 2064426: case 1474603: case 884780: case 294957: 
                    case 3833904: case 3244081: case 2654258: case 2064435: case 1474612: case 884789: 
                    case 294966: case 3833913: case 3244090: case 2654267: 
                        tenemy_mask0 += 16; continue;
                    case 360448: case 3899395: case 3309572: case 2719749: case 2129926: case 1540103: 
                    case 950280: case 360457: case 3899404: case 3309581: case 2719758: case 2129935: 
                    case 1540112: case 950289: case 360466: case 3899413: case 3309590: case 2719767: 
                    case 2129944: case 1540121: case 950298: case 360475: case 3899422: case 3309599: 
                    case 2719776: case 2129953: case 1540130: case 950307: case 360484: case 3899431: 
                    case 3309608: case 2719785: case 2129962: case 1540139: case 950316: case 360493: 
                    case 3899440: case 3309617: case 2719794: case 2129971: case 1540148: case 950325: 
                    case 360502: case 3899449: case 3309626: case 2719803: 
                        tenemy_mask0 += 32; continue;
                    case 425984: case 3375108: case 2785285: case 2195462: case 1605639: case 1015816: 
                    case 425993: case 3375117: case 2785294: case 2195471: case 1605648: case 1015825: 
                    case 426002: case 3375126: case 2785303: case 2195480: case 1605657: case 1015834: 
                    case 426011: case 3375135: case 2785312: case 2195489: case 1605666: case 1015843: 
                    case 426020: case 3375144: case 2785321: case 2195498: case 1605675: case 1015852: 
                    case 426029: case 3375153: case 2785330: case 2195507: case 1605684: case 1015861: 
                    case 426038: case 3375162: case 2785339: 
                        tenemy_mask0 += 64; continue;
                    case 491520: case 3440644: case 2850821: case 2260998: case 1671175: case 1081352: 
                    case 491529: case 3440653: case 2850830: case 2261007: case 1671184: case 1081361: 
                    case 491538: case 3440662: case 2850839: case 2261016: case 1671193: case 1081370: 
                    case 491547: case 3440671: case 2850848: case 2261025: case 1671202: case 1081379: 
                    case 491556: case 3440680: case 2850857: case 2261034: case 1671211: case 1081388: 
                    case 491565: case 3440689: case 2850866: case 2261043: case 1671220: case 1081397: 
                    case 491574: case 3440698: case 2850875: 
                        tenemy_mask0 += 128; continue;
                    case 557056: case 3506180: case 2916357: case 2326534: case 1736711: case 1146888: 
                    case 557065: case 3506189: case 2916366: case 2326543: case 1736720: case 1146897: 
                    case 557074: case 3506198: case 2916375: case 2326552: case 1736729: case 1146906: 
                    case 557083: case 3506207: case 2916384: case 2326561: case 1736738: case 1146915: 
                    case 557092: case 3506216: case 2916393: case 2326570: case 1736747: case 1146924: 
                    case 557101: case 3506225: case 2916402: case 2326579: case 1736756: case 1146933: 
                    case 557110: case 3506234: case 2916411: 
                        tenemy_mask0 += 256; continue;
                    case 622592: case 32769: case 3571716: case 2981893: case 2392070: case 1802247: 
                    case 1212424: case 622601: case 32778: case 3571725: case 2981902: case 2392079: 
                    case 1802256: case 1212433: case 622610: case 32787: case 3571734: case 2981911: 
                    case 2392088: case 1802265: case 1212442: case 622619: case 32796: case 3571743: 
                    case 2981920: case 2392097: case 1802274: case 1212451: case 622628: case 32805: 
                    case 3571752: case 2981929: case 2392106: case 1802283: case 1212460: case 622637: 
                    case 32814: case 3571761: case 2981938: case 2392115: case 1802292: case 1212469: 
                    case 622646: case 32823: case 3571770: case 2981947: 
                        tenemy_mask0 += 512; continue;
                    case 688128: case 98305: case 3637252: case 3047429: case 2457606: case 1867783: 
                    case 1277960: case 688137: case 98314: case 3637261: case 3047438: case 2457615: 
                    case 1867792: case 1277969: case 688146: case 98323: case 3637270: case 3047447: 
                    case 2457624: case 1867801: case 1277978: case 688155: case 98332: case 3637279: 
                    case 3047456: case 2457633: case 1867810: case 1277987: case 688164: case 98341: 
                    case 3637288: case 3047465: case 2457642: case 1867819: case 1277996: case 688173: 
                    case 98350: case 3637297: case 3047474: case 2457651: case 1867828: case 1278005: 
                    case 688182: case 98359: case 3637306: case 3047483: 
                        tenemy_mask0 += 1024; continue;
                    case 753664: case 163841: case 3702788: case 3112965: case 2523142: case 1933319: 
                    case 1343496: case 753673: case 163850: case 3702797: case 3112974: case 2523151: 
                    case 1933328: case 1343505: case 753682: case 163859: case 3702806: case 3112983: 
                    case 2523160: case 1933337: case 1343514: case 753691: case 163868: case 3702815: 
                    case 3112992: case 2523169: case 1933346: case 1343523: case 753700: case 163877: 
                    case 3702824: case 3113001: case 2523178: case 1933355: case 1343532: case 753709: 
                    case 163886: case 3702833: case 3113010: case 2523187: case 1933364: case 1343541: 
                    case 753718: case 163895: case 3702842: case 3113019: 
                        tenemy_mask0 += 2048; continue;
                    case 819200: case 229377: case 3768324: case 3178501: case 2588678: case 1998855: 
                    case 1409032: case 819209: case 229386: case 3768333: case 3178510: case 2588687: 
                    case 1998864: case 1409041: case 819218: case 229395: case 3768342: case 3178519: 
                    case 2588696: case 1998873: case 1409050: case 819227: case 229404: case 3768351: 
                    case 3178528: case 2588705: case 1998882: case 1409059: case 819236: case 229413: 
                    case 3768360: case 3178537: case 2588714: case 1998891: case 1409068: case 819245: 
                    case 229422: case 3768369: case 3178546: case 2588723: case 1998900: case 1409077: 
                    case 819254: case 229431: case 3768378: case 3178555: 
                        tenemy_mask0 += 4096; continue;
                    case 884736: case 294913: case 3833860: case 3244037: case 2654214: case 2064391: 
                    case 1474568: case 884745: case 294922: case 3833869: case 3244046: case 2654223: 
                    case 2064400: case 1474577: case 884754: case 294931: case 3833878: case 3244055: 
                    case 2654232: case 2064409: case 1474586: case 884763: case 294940: case 3833887: 
                    case 3244064: case 2654241: case 2064418: case 1474595: case 884772: case 294949: 
                    case 3833896: case 3244073: case 2654250: case 2064427: case 1474604: case 884781: 
                    case 294958: case 3833905: case 3244082: case 2654259: case 2064436: case 1474613: 
                    case 884790: case 294967: case 3833914: case 3244091: 
                        tenemy_mask0 += 8192; continue;
                    case 950272: case 360449: case 3899396: case 3309573: case 2719750: case 2129927: 
                    case 1540104: case 950281: case 360458: case 3899405: case 3309582: case 2719759: 
                    case 2129936: case 1540113: case 950290: case 360467: case 3899414: case 3309591: 
                    case 2719768: case 2129945: case 1540122: case 950299: case 360476: case 3899423: 
                    case 3309600: case 2719777: case 2129954: case 1540131: case 950308: case 360485: 
                    case 3899432: case 3309609: case 2719786: case 2129963: case 1540140: case 950317: 
                    case 360494: case 3899441: case 3309618: case 2719795: case 2129972: case 1540149: 
                    case 950326: case 360503: case 3899450: case 3309627: 
                        tenemy_mask0 += 16384; continue;
                    case 1015808: case 425985: case 3375109: case 2785286: case 2195463: case 1605640: 
                    case 1015817: case 425994: case 3375118: case 2785295: case 2195472: case 1605649: 
                    case 1015826: case 426003: case 3375127: case 2785304: case 2195481: case 1605658: 
                    case 1015835: case 426012: case 3375136: case 2785313: case 2195490: case 1605667: 
                    case 1015844: case 426021: case 3375145: case 2785322: case 2195499: case 1605676: 
                    case 1015853: case 426030: case 3375154: case 2785331: case 2195508: case 1605685: 
                    case 1015862: case 426039: case 3375163: 
                        tenemy_mask0 += 16384;
                        tenemy_mask0 += 16384;
                        continue;
                    case 1081344: case 491521: case 3440645: case 2850822: case 2260999: case 1671176: 
                    case 1081353: case 491530: case 3440654: case 2850831: case 2261008: case 1671185: 
                    case 1081362: case 491539: case 3440663: case 2850840: case 2261017: case 1671194: 
                    case 1081371: case 491548: case 3440672: case 2850849: case 2261026: case 1671203: 
                    case 1081380: case 491557: case 3440681: case 2850858: case 2261035: case 1671212: 
                    case 1081389: case 491566: case 3440690: case 2850867: case 2261044: case 1671221: 
                    case 1081398: case 491575: case 3440699: 
                        tenemy_mask1 += 1; continue;
                    case 1146880: case 557057: case 3506181: case 2916358: case 2326535: case 1736712: 
                    case 1146889: case 557066: case 3506190: case 2916367: case 2326544: case 1736721: 
                    case 1146898: case 557075: case 3506199: case 2916376: case 2326553: case 1736730: 
                    case 1146907: case 557084: case 3506208: case 2916385: case 2326562: case 1736739: 
                    case 1146916: case 557093: case 3506217: case 2916394: case 2326571: case 1736748: 
                    case 1146925: case 557102: case 3506226: case 2916403: case 2326580: case 1736757: 
                    case 1146934: case 557111: case 3506235: 
                        tenemy_mask1 += 2; continue;
                    case 1212416: case 622593: case 32770: case 3571717: case 2981894: case 2392071: 
                    case 1802248: case 1212425: case 622602: case 32779: case 3571726: case 2981903: 
                    case 2392080: case 1802257: case 1212434: case 622611: case 32788: case 3571735: 
                    case 2981912: case 2392089: case 1802266: case 1212443: case 622620: case 32797: 
                    case 3571744: case 2981921: case 2392098: case 1802275: case 1212452: case 622629: 
                    case 32806: case 3571753: case 2981930: case 2392107: case 1802284: case 1212461: 
                    case 622638: case 32815: case 3571762: case 2981939: case 2392116: case 1802293: 
                    case 1212470: case 622647: case 32824: case 3571771: 
                        tenemy_mask1 += 4; continue;
                    case 1277952: case 688129: case 98306: case 3637253: case 3047430: case 2457607: 
                    case 1867784: case 1277961: case 688138: case 98315: case 3637262: case 3047439: 
                    case 2457616: case 1867793: case 1277970: case 688147: case 98324: case 3637271: 
                    case 3047448: case 2457625: case 1867802: case 1277979: case 688156: case 98333: 
                    case 3637280: case 3047457: case 2457634: case 1867811: case 1277988: case 688165: 
                    case 98342: case 3637289: case 3047466: case 2457643: case 1867820: case 1277997: 
                    case 688174: case 98351: case 3637298: case 3047475: case 2457652: case 1867829: 
                    case 1278006: case 688183: case 98360: case 3637307: 
                        tenemy_mask1 += 8; continue;
                    case 1343488: case 753665: case 163842: case 3702789: case 3112966: case 2523143: 
                    case 1933320: case 1343497: case 753674: case 163851: case 3702798: case 3112975: 
                    case 2523152: case 1933329: case 1343506: case 753683: case 163860: case 3702807: 
                    case 3112984: case 2523161: case 1933338: case 1343515: case 753692: case 163869: 
                    case 3702816: case 3112993: case 2523170: case 1933347: case 1343524: case 753701: 
                    case 163878: case 3702825: case 3113002: case 2523179: case 1933356: case 1343533: 
                    case 753710: case 163887: case 3702834: case 3113011: case 2523188: case 1933365: 
                    case 1343542: case 753719: case 163896: case 3702843: 
                        tenemy_mask1 += 16; continue;
                    case 1409024: case 819201: case 229378: case 3768325: case 3178502: case 2588679: 
                    case 1998856: case 1409033: case 819210: case 229387: case 3768334: case 3178511: 
                    case 2588688: case 1998865: case 1409042: case 819219: case 229396: case 3768343: 
                    case 3178520: case 2588697: case 1998874: case 1409051: case 819228: case 229405: 
                    case 3768352: case 3178529: case 2588706: case 1998883: case 1409060: case 819237: 
                    case 229414: case 3768361: case 3178538: case 2588715: case 1998892: case 1409069: 
                    case 819246: case 229423: case 3768370: case 3178547: case 2588724: case 1998901: 
                    case 1409078: case 819255: case 229432: case 3768379: 
                        tenemy_mask1 += 32; continue;
                    case 1474560: case 884737: case 294914: case 3833861: case 3244038: case 2654215: 
                    case 2064392: case 1474569: case 884746: case 294923: case 3833870: case 3244047: 
                    case 2654224: case 2064401: case 1474578: case 884755: case 294932: case 3833879: 
                    case 3244056: case 2654233: case 2064410: case 1474587: case 884764: case 294941: 
                    case 3833888: case 3244065: case 2654242: case 2064419: case 1474596: case 884773: 
                    case 294950: case 3833897: case 3244074: case 2654251: case 2064428: case 1474605: 
                    case 884782: case 294959: case 3833906: case 3244083: case 2654260: case 2064437: 
                    case 1474614: case 884791: case 294968: case 3833915: 
                        tenemy_mask1 += 64; continue;
                    case 1540096: case 950273: case 360450: case 3899397: case 3309574: case 2719751: 
                    case 2129928: case 1540105: case 950282: case 360459: case 3899406: case 3309583: 
                    case 2719760: case 2129937: case 1540114: case 950291: case 360468: case 3899415: 
                    case 3309592: case 2719769: case 2129946: case 1540123: case 950300: case 360477: 
                    case 3899424: case 3309601: case 2719778: case 2129955: case 1540132: case 950309: 
                    case 360486: case 3899433: case 3309610: case 2719787: case 2129964: case 1540141: 
                    case 950318: case 360495: case 3899442: case 3309619: case 2719796: case 2129973: 
                    case 1540150: case 950327: case 360504: case 3899451: 
                        tenemy_mask1 += 128; continue;
                    case 1605632: case 1015809: case 425986: case 3375110: case 2785287: case 2195464: 
                    case 1605641: case 1015818: case 425995: case 3375119: case 2785296: case 2195473: 
                    case 1605650: case 1015827: case 426004: case 3375128: case 2785305: case 2195482: 
                    case 1605659: case 1015836: case 426013: case 3375137: case 2785314: case 2195491: 
                    case 1605668: case 1015845: case 426022: case 3375146: case 2785323: case 2195500: 
                    case 1605677: case 1015854: case 426031: case 3375155: case 2785332: case 2195509: 
                    case 1605686: case 1015863: case 426040: 
                        tenemy_mask1 += 256; continue;
                    case 1671168: case 1081345: case 491522: case 3440646: case 2850823: case 2261000: 
                    case 1671177: case 1081354: case 491531: case 3440655: case 2850832: case 2261009: 
                    case 1671186: case 1081363: case 491540: case 3440664: case 2850841: case 2261018: 
                    case 1671195: case 1081372: case 491549: case 3440673: case 2850850: case 2261027: 
                    case 1671204: case 1081381: case 491558: case 3440682: case 2850859: case 2261036: 
                    case 1671213: case 1081390: case 491567: case 3440691: case 2850868: case 2261045: 
                    case 1671222: case 1081399: case 491576: 
                        tenemy_mask1 += 512; continue;
                    case 1736704: case 1146881: case 557058: case 3506182: case 2916359: case 2326536: 
                    case 1736713: case 1146890: case 557067: case 3506191: case 2916368: case 2326545: 
                    case 1736722: case 1146899: case 557076: case 3506200: case 2916377: case 2326554: 
                    case 1736731: case 1146908: case 557085: case 3506209: case 2916386: case 2326563: 
                    case 1736740: case 1146917: case 557094: case 3506218: case 2916395: case 2326572: 
                    case 1736749: case 1146926: case 557103: case 3506227: case 2916404: case 2326581: 
                    case 1736758: case 1146935: case 557112: 
                        tenemy_mask1 += 1024; continue;
                    case 1802240: case 1212417: case 622594: case 32771: case 3571718: case 2981895: 
                    case 2392072: case 1802249: case 1212426: case 622603: case 32780: case 3571727: 
                    case 2981904: case 2392081: case 1802258: case 1212435: case 622612: case 32789: 
                    case 3571736: case 2981913: case 2392090: case 1802267: case 1212444: case 622621: 
                    case 32798: case 3571745: case 2981922: case 2392099: case 1802276: case 1212453: 
                    case 622630: case 32807: case 3571754: case 2981931: case 2392108: case 1802285: 
                    case 1212462: case 622639: case 32816: case 3571763: case 2981940: case 2392117: 
                    case 1802294: case 1212471: case 622648: case 32825: 
                        tenemy_mask1 += 2048; continue;
                    case 1867776: case 1277953: case 688130: case 98307: case 3637254: case 3047431: 
                    case 2457608: case 1867785: case 1277962: case 688139: case 98316: case 3637263: 
                    case 3047440: case 2457617: case 1867794: case 1277971: case 688148: case 98325: 
                    case 3637272: case 3047449: case 2457626: case 1867803: case 1277980: case 688157: 
                    case 98334: case 3637281: case 3047458: case 2457635: case 1867812: case 1277989: 
                    case 688166: case 98343: case 3637290: case 3047467: case 2457644: case 1867821: 
                    case 1277998: case 688175: case 98352: case 3637299: case 3047476: case 2457653: 
                    case 1867830: case 1278007: case 688184: case 98361: 
                        tenemy_mask1 += 4096; continue;
                    case 1933312: case 1343489: case 753666: case 163843: case 3702790: case 3112967: 
                    case 2523144: case 1933321: case 1343498: case 753675: case 163852: case 3702799: 
                    case 3112976: case 2523153: case 1933330: case 1343507: case 753684: case 163861: 
                    case 3702808: case 3112985: case 2523162: case 1933339: case 1343516: case 753693: 
                    case 163870: case 3702817: case 3112994: case 2523171: case 1933348: case 1343525: 
                    case 753702: case 163879: case 3702826: case 3113003: case 2523180: case 1933357: 
                    case 1343534: case 753711: case 163888: case 3702835: case 3113012: case 2523189: 
                    case 1933366: case 1343543: case 753720: case 163897: 
                        tenemy_mask1 += 8192; continue;
                    case 1998848: case 1409025: case 819202: case 229379: case 3768326: case 3178503: 
                    case 2588680: case 1998857: case 1409034: case 819211: case 229388: case 3768335: 
                    case 3178512: case 2588689: case 1998866: case 1409043: case 819220: case 229397: 
                    case 3768344: case 3178521: case 2588698: case 1998875: case 1409052: case 819229: 
                    case 229406: case 3768353: case 3178530: case 2588707: case 1998884: case 1409061: 
                    case 819238: case 229415: case 3768362: case 3178539: case 2588716: case 1998893: 
                    case 1409070: case 819247: case 229424: case 3768371: case 3178548: case 2588725: 
                    case 1998902: case 1409079: case 819256: case 229433: 
                        tenemy_mask1 += 16384; continue;
                    case 2064384: case 1474561: case 884738: case 294915: case 3833862: case 3244039: 
                    case 2654216: case 2064393: case 1474570: case 884747: case 294924: case 3833871: 
                    case 3244048: case 2654225: case 2064402: case 1474579: case 884756: case 294933: 
                    case 3833880: case 3244057: case 2654234: case 2064411: case 1474588: case 884765: 
                    case 294942: case 3833889: case 3244066: case 2654243: case 2064420: case 1474597: 
                    case 884774: case 294951: case 3833898: case 3244075: case 2654252: case 2064429: 
                    case 1474606: case 884783: case 294960: case 3833907: case 3244084: case 2654261: 
                    case 2064438: case 1474615: case 884792: case 294969: 
                        tenemy_mask1 += 16384;
                        tenemy_mask1 += 16384;
                        continue;
                    case 2129920: case 1540097: case 950274: case 360451: case 3899398: case 3309575: 
                    case 2719752: case 2129929: case 1540106: case 950283: case 360460: case 3899407: 
                    case 3309584: case 2719761: case 2129938: case 1540115: case 950292: case 360469: 
                    case 3899416: case 3309593: case 2719770: case 2129947: case 1540124: case 950301: 
                    case 360478: case 3899425: case 3309602: case 2719779: case 2129956: case 1540133: 
                    case 950310: case 360487: case 3899434: case 3309611: case 2719788: case 2129965: 
                    case 1540142: case 950319: case 360496: case 3899443: case 3309620: case 2719797: 
                    case 2129974: case 1540151: case 950328: case 360505: 
                        tenemy_mask2 += 1; continue;
                    case 2195456: case 1605633: case 1015810: case 425987: case 3375111: case 2785288: 
                    case 2195465: case 1605642: case 1015819: case 425996: case 3375120: case 2785297: 
                    case 2195474: case 1605651: case 1015828: case 426005: case 3375129: case 2785306: 
                    case 2195483: case 1605660: case 1015837: case 426014: case 3375138: case 2785315: 
                    case 2195492: case 1605669: case 1015846: case 426023: case 3375147: case 2785324: 
                    case 2195501: case 1605678: case 1015855: case 426032: case 3375156: case 2785333: 
                    case 2195510: case 1605687: case 1015864: case 426041: 
                        tenemy_mask2 += 2; continue;
                    case 2260992: case 1671169: case 1081346: case 491523: case 3440647: case 2850824: 
                    case 2261001: case 1671178: case 1081355: case 491532: case 3440656: case 2850833: 
                    case 2261010: case 1671187: case 1081364: case 491541: case 3440665: case 2850842: 
                    case 2261019: case 1671196: case 1081373: case 491550: case 3440674: case 2850851: 
                    case 2261028: case 1671205: case 1081382: case 491559: case 3440683: case 2850860: 
                    case 2261037: case 1671214: case 1081391: case 491568: case 3440692: case 2850869: 
                    case 2261046: case 1671223: case 1081400: case 491577: 
                        tenemy_mask2 += 4; continue;
                    case 2326528: case 1736705: case 1146882: case 557059: case 3506183: case 2916360: 
                    case 2326537: case 1736714: case 1146891: case 557068: case 3506192: case 2916369: 
                    case 2326546: case 1736723: case 1146900: case 557077: case 3506201: case 2916378: 
                    case 2326555: case 1736732: case 1146909: case 557086: case 3506210: case 2916387: 
                    case 2326564: case 1736741: case 1146918: case 557095: case 3506219: case 2916396: 
                    case 2326573: case 1736750: case 1146927: case 557104: case 3506228: case 2916405: 
                    case 2326582: case 1736759: case 1146936: case 557113: 
                        tenemy_mask2 += 8; continue;
                    case 2392064: case 1802241: case 1212418: case 622595: case 32772: case 3571719: 
                    case 2981896: case 2392073: case 1802250: case 1212427: case 622604: case 32781: 
                    case 3571728: case 2981905: case 2392082: case 1802259: case 1212436: case 622613: 
                    case 32790: case 3571737: case 2981914: case 2392091: case 1802268: case 1212445: 
                    case 622622: case 32799: case 3571746: case 2981923: case 2392100: case 1802277: 
                    case 1212454: case 622631: case 32808: case 3571755: case 2981932: case 2392109: 
                    case 1802286: case 1212463: case 622640: case 32817: case 3571764: case 2981941: 
                    case 2392118: case 1802295: case 1212472: case 622649: case 32826: 
                        tenemy_mask2 += 16; continue;
                    case 2457600: case 1867777: case 1277954: case 688131: case 98308: case 3637255: 
                    case 3047432: case 2457609: case 1867786: case 1277963: case 688140: case 98317: 
                    case 3637264: case 3047441: case 2457618: case 1867795: case 1277972: case 688149: 
                    case 98326: case 3637273: case 3047450: case 2457627: case 1867804: case 1277981: 
                    case 688158: case 98335: case 3637282: case 3047459: case 2457636: case 1867813: 
                    case 1277990: case 688167: case 98344: case 3637291: case 3047468: case 2457645: 
                    case 1867822: case 1277999: case 688176: case 98353: case 3637300: case 3047477: 
                    case 2457654: case 1867831: case 1278008: case 688185: case 98362: 
                        tenemy_mask2 += 32; continue;
                    case 2523136: case 1933313: case 1343490: case 753667: case 163844: case 3702791: 
                    case 3112968: case 2523145: case 1933322: case 1343499: case 753676: case 163853: 
                    case 3702800: case 3112977: case 2523154: case 1933331: case 1343508: case 753685: 
                    case 163862: case 3702809: case 3112986: case 2523163: case 1933340: case 1343517: 
                    case 753694: case 163871: case 3702818: case 3112995: case 2523172: case 1933349: 
                    case 1343526: case 753703: case 163880: case 3702827: case 3113004: case 2523181: 
                    case 1933358: case 1343535: case 753712: case 163889: case 3702836: case 3113013: 
                    case 2523190: case 1933367: case 1343544: case 753721: case 163898: 
                        tenemy_mask2 += 64; continue;
                    case 2588672: case 1998849: case 1409026: case 819203: case 229380: case 3768327: 
                    case 3178504: case 2588681: case 1998858: case 1409035: case 819212: case 229389: 
                    case 3768336: case 3178513: case 2588690: case 1998867: case 1409044: case 819221: 
                    case 229398: case 3768345: case 3178522: case 2588699: case 1998876: case 1409053: 
                    case 819230: case 229407: case 3768354: case 3178531: case 2588708: case 1998885: 
                    case 1409062: case 819239: case 229416: case 3768363: case 3178540: case 2588717: 
                    case 1998894: case 1409071: case 819248: case 229425: case 3768372: case 3178549: 
                    case 2588726: case 1998903: case 1409080: case 819257: case 229434: 
                        tenemy_mask2 += 128; continue;
                    case 2654208: case 2064385: case 1474562: case 884739: case 294916: case 3833863: 
                    case 3244040: case 2654217: case 2064394: case 1474571: case 884748: case 294925: 
                    case 3833872: case 3244049: case 2654226: case 2064403: case 1474580: case 884757: 
                    case 294934: case 3833881: case 3244058: case 2654235: case 2064412: case 1474589: 
                    case 884766: case 294943: case 3833890: case 3244067: case 2654244: case 2064421: 
                    case 1474598: case 884775: case 294952: case 3833899: case 3244076: case 2654253: 
                    case 2064430: case 1474607: case 884784: case 294961: case 3833908: case 3244085: 
                    case 2654262: case 2064439: case 1474616: case 884793: case 294970: 
                        tenemy_mask2 += 256; continue;
                    case 2719744: case 2129921: case 1540098: case 950275: case 360452: case 3899399: 
                    case 3309576: case 2719753: case 2129930: case 1540107: case 950284: case 360461: 
                    case 3899408: case 3309585: case 2719762: case 2129939: case 1540116: case 950293: 
                    case 360470: case 3899417: case 3309594: case 2719771: case 2129948: case 1540125: 
                    case 950302: case 360479: case 3899426: case 3309603: case 2719780: case 2129957: 
                    case 1540134: case 950311: case 360488: case 3899435: case 3309612: case 2719789: 
                    case 2129966: case 1540143: case 950320: case 360497: case 3899444: case 3309621: 
                    case 2719798: case 2129975: case 1540152: case 950329: case 360506: 
                        tenemy_mask2 += 512; continue;
                    case 2785280: case 2195457: case 1605634: case 1015811: case 425988: case 3375112: 
                    case 2785289: case 2195466: case 1605643: case 1015820: case 425997: case 3375121: 
                    case 2785298: case 2195475: case 1605652: case 1015829: case 426006: case 3375130: 
                    case 2785307: case 2195484: case 1605661: case 1015838: case 426015: case 3375139: 
                    case 2785316: case 2195493: case 1605670: case 1015847: case 426024: case 3375148: 
                    case 2785325: case 2195502: case 1605679: case 1015856: case 426033: case 3375157: 
                    case 2785334: case 2195511: case 1605688: case 1015865: case 426042: 
                        tenemy_mask2 += 1024; continue;
                    case 2850816: case 2260993: case 1671170: case 1081347: case 491524: case 3440648: 
                    case 2850825: case 2261002: case 1671179: case 1081356: case 491533: case 3440657: 
                    case 2850834: case 2261011: case 1671188: case 1081365: case 491542: case 3440666: 
                    case 2850843: case 2261020: case 1671197: case 1081374: case 491551: case 3440675: 
                    case 2850852: case 2261029: case 1671206: case 1081383: case 491560: case 3440684: 
                    case 2850861: case 2261038: case 1671215: case 1081392: case 491569: case 3440693: 
                    case 2850870: case 2261047: case 1671224: case 1081401: case 491578: 
                        tenemy_mask2 += 2048; continue;
                    case 2916352: case 2326529: case 1736706: case 1146883: case 557060: case 3506184: 
                    case 2916361: case 2326538: case 1736715: case 1146892: case 557069: case 3506193: 
                    case 2916370: case 2326547: case 1736724: case 1146901: case 557078: case 3506202: 
                    case 2916379: case 2326556: case 1736733: case 1146910: case 557087: case 3506211: 
                    case 2916388: case 2326565: case 1736742: case 1146919: case 557096: case 3506220: 
                    case 2916397: case 2326574: case 1736751: case 1146928: case 557105: case 3506229: 
                    case 2916406: case 2326583: case 1736760: case 1146937: case 557114: 
                        tenemy_mask2 += 4096; continue;
                    case 2981888: case 2392065: case 1802242: case 1212419: case 622596: case 32773: 
                    case 3571720: case 2981897: case 2392074: case 1802251: case 1212428: case 622605: 
                    case 32782: case 3571729: case 2981906: case 2392083: case 1802260: case 1212437: 
                    case 622614: case 32791: case 3571738: case 2981915: case 2392092: case 1802269: 
                    case 1212446: case 622623: case 32800: case 3571747: case 2981924: case 2392101: 
                    case 1802278: case 1212455: case 622632: case 32809: case 3571756: case 2981933: 
                    case 2392110: case 1802287: case 1212464: case 622641: case 32818: case 3571765: 
                    case 2981942: case 2392119: case 1802296: case 1212473: case 622650: case 32827: 
                        tenemy_mask2 += 8192; continue;
                    case 3047424: case 2457601: case 1867778: case 1277955: case 688132: case 98309: 
                    case 3637256: case 3047433: case 2457610: case 1867787: case 1277964: case 688141: 
                    case 98318: case 3637265: case 3047442: case 2457619: case 1867796: case 1277973: 
                    case 688150: case 98327: case 3637274: case 3047451: case 2457628: case 1867805: 
                    case 1277982: case 688159: case 98336: case 3637283: case 3047460: case 2457637: 
                    case 1867814: case 1277991: case 688168: case 98345: case 3637292: case 3047469: 
                    case 2457646: case 1867823: case 1278000: case 688177: case 98354: case 3637301: 
                    case 3047478: case 2457655: case 1867832: case 1278009: case 688186: case 98363: 
                        tenemy_mask2 += 16384; continue;
                    case 3112960: case 2523137: case 1933314: case 1343491: case 753668: case 163845: 
                    case 3702792: case 3112969: case 2523146: case 1933323: case 1343500: case 753677: 
                    case 163854: case 3702801: case 3112978: case 2523155: case 1933332: case 1343509: 
                    case 753686: case 163863: case 3702810: case 3112987: case 2523164: case 1933341: 
                    case 1343518: case 753695: case 163872: case 3702819: case 3112996: case 2523173: 
                    case 1933350: case 1343527: case 753704: case 163881: case 3702828: case 3113005: 
                    case 2523182: case 1933359: case 1343536: case 753713: case 163890: case 3702837: 
                    case 3113014: case 2523191: case 1933368: case 1343545: case 753722: case 163899: 
                        tenemy_mask2 += 16384;
                        tenemy_mask2 += 16384;
                        continue;
                    case 3178496: case 2588673: case 1998850: case 1409027: case 819204: case 229381: 
                    case 3768328: case 3178505: case 2588682: case 1998859: case 1409036: case 819213: 
                    case 229390: case 3768337: case 3178514: case 2588691: case 1998868: case 1409045: 
                    case 819222: case 229399: case 3768346: case 3178523: case 2588700: case 1998877: 
                    case 1409054: case 819231: case 229408: case 3768355: case 3178532: case 2588709: 
                    case 1998886: case 1409063: case 819240: case 229417: case 3768364: case 3178541: 
                    case 2588718: case 1998895: case 1409072: case 819249: case 229426: case 3768373: 
                    case 3178550: case 2588727: case 1998904: case 1409081: case 819258: case 229435: 
                        tenemy_mask3 += 1; continue;
                    case 3244032: case 2654209: case 2064386: case 1474563: case 884740: case 294917: 
                    case 3833864: case 3244041: case 2654218: case 2064395: case 1474572: case 884749: 
                    case 294926: case 3833873: case 3244050: case 2654227: case 2064404: case 1474581: 
                    case 884758: case 294935: case 3833882: case 3244059: case 2654236: case 2064413: 
                    case 1474590: case 884767: case 294944: case 3833891: case 3244068: case 2654245: 
                    case 2064422: case 1474599: case 884776: case 294953: case 3833900: case 3244077: 
                    case 2654254: case 2064431: case 1474608: case 884785: case 294962: case 3833909: 
                    case 3244086: case 2654263: case 2064440: case 1474617: case 884794: case 294971: 
                        tenemy_mask3 += 2; continue;
                    case 3309568: case 2719745: case 2129922: case 1540099: case 950276: case 360453: 
                    case 3899400: case 3309577: case 2719754: case 2129931: case 1540108: case 950285: 
                    case 360462: case 3899409: case 3309586: case 2719763: case 2129940: case 1540117: 
                    case 950294: case 360471: case 3899418: case 3309595: case 2719772: case 2129949: 
                    case 1540126: case 950303: case 360480: case 3899427: case 3309604: case 2719781: 
                    case 2129958: case 1540135: case 950312: case 360489: case 3899436: case 3309613: 
                    case 2719790: case 2129967: case 1540144: case 950321: case 360498: case 3899445: 
                    case 3309622: case 2719799: case 2129976: case 1540153: case 950330: case 360507: 
                        tenemy_mask3 += 4; continue;
                    case 3375104: case 2785281: case 2195458: case 1605635: case 1015812: case 425989: 
                    case 3375113: case 2785290: case 2195467: case 1605644: case 1015821: case 425998: 
                    case 3375122: case 2785299: case 2195476: case 1605653: case 1015830: case 426007: 
                    case 3375131: case 2785308: case 2195485: case 1605662: case 1015839: case 426016: 
                    case 3375140: case 2785317: case 2195494: case 1605671: case 1015848: case 426025: 
                    case 3375149: case 2785326: case 2195503: case 1605680: case 1015857: case 426034: 
                    case 3375158: case 2785335: case 2195512: case 1605689: case 1015866: case 426043: 
                        tenemy_mask3 += 8; continue;
                    case 3440640: case 2850817: case 2260994: case 1671171: case 1081348: case 491525: 
                    case 3440649: case 2850826: case 2261003: case 1671180: case 1081357: case 491534: 
                    case 3440658: case 2850835: case 2261012: case 1671189: case 1081366: case 491543: 
                    case 3440667: case 2850844: case 2261021: case 1671198: case 1081375: case 491552: 
                    case 3440676: case 2850853: case 2261030: case 1671207: case 1081384: case 491561: 
                    case 3440685: case 2850862: case 2261039: case 1671216: case 1081393: case 491570: 
                    case 3440694: case 2850871: case 2261048: case 1671225: case 1081402: case 491579: 
                        tenemy_mask3 += 16; continue;
                    case 3506176: case 2916353: case 2326530: case 1736707: case 1146884: case 557061: 
                    case 3506185: case 2916362: case 2326539: case 1736716: case 1146893: case 557070: 
                    case 3506194: case 2916371: case 2326548: case 1736725: case 1146902: case 557079: 
                    case 3506203: case 2916380: case 2326557: case 1736734: case 1146911: case 557088: 
                    case 3506212: case 2916389: case 2326566: case 1736743: case 1146920: case 557097: 
                    case 3506221: case 2916398: case 2326575: case 1736752: case 1146929: case 557106: 
                    case 3506230: case 2916407: case 2326584: case 1736761: case 1146938: case 557115: 
                        tenemy_mask3 += 32; continue;
                    case 3571712: case 2981889: case 2392066: case 1802243: case 1212420: case 622597: 
                    case 32774: case 3571721: case 2981898: case 2392075: case 1802252: case 1212429: 
                    case 622606: case 32783: case 3571730: case 2981907: case 2392084: case 1802261: 
                    case 1212438: case 622615: case 32792: case 3571739: case 2981916: case 2392093: 
                    case 1802270: case 1212447: case 622624: case 32801: case 3571748: case 2981925: 
                    case 2392102: case 1802279: case 1212456: case 622633: case 32810: case 3571757: 
                    case 2981934: case 2392111: case 1802288: case 1212465: case 622642: case 32819: 
                    case 3571766: case 2981943: case 2392120: case 1802297: case 1212474: case 622651: 
                        tenemy_mask3 += 64; continue;
                    case 3637248: case 3047425: case 2457602: case 1867779: case 1277956: case 688133: 
                    case 98310: case 3637257: case 3047434: case 2457611: case 1867788: case 1277965: 
                    case 688142: case 98319: case 3637266: case 3047443: case 2457620: case 1867797: 
                    case 1277974: case 688151: case 98328: case 3637275: case 3047452: case 2457629: 
                    case 1867806: case 1277983: case 688160: case 98337: case 3637284: case 3047461: 
                    case 2457638: case 1867815: case 1277992: case 688169: case 98346: case 3637293: 
                    case 3047470: case 2457647: case 1867824: case 1278001: case 688178: case 98355: 
                    case 3637302: case 3047479: case 2457656: case 1867833: case 1278010: case 688187: 
                        tenemy_mask3 += 128; continue;
                    case 3702784: case 3112961: case 2523138: case 1933315: case 1343492: case 753669: 
                    case 163846: case 3702793: case 3112970: case 2523147: case 1933324: case 1343501: 
                    case 753678: case 163855: case 3702802: case 3112979: case 2523156: case 1933333: 
                    case 1343510: case 753687: case 163864: case 3702811: case 3112988: case 2523165: 
                    case 1933342: case 1343519: case 753696: case 163873: case 3702820: case 3112997: 
                    case 2523174: case 1933351: case 1343528: case 753705: case 163882: case 3702829: 
                    case 3113006: case 2523183: case 1933360: case 1343537: case 753714: case 163891: 
                    case 3702838: case 3113015: case 2523192: case 1933369: case 1343546: case 753723: 
                        tenemy_mask3 += 256; continue;
                    case 3768320: case 3178497: case 2588674: case 1998851: case 1409028: case 819205: 
                    case 229382: case 3768329: case 3178506: case 2588683: case 1998860: case 1409037: 
                    case 819214: case 229391: case 3768338: case 3178515: case 2588692: case 1998869: 
                    case 1409046: case 819223: case 229400: case 3768347: case 3178524: case 2588701: 
                    case 1998878: case 1409055: case 819232: case 229409: case 3768356: case 3178533: 
                    case 2588710: case 1998887: case 1409064: case 819241: case 229418: case 3768365: 
                    case 3178542: case 2588719: case 1998896: case 1409073: case 819250: case 229427: 
                    case 3768374: case 3178551: case 2588728: case 1998905: case 1409082: case 819259: 
                        tenemy_mask3 += 512; continue;
                    case 3833856: case 3244033: case 2654210: case 2064387: case 1474564: case 884741: 
                    case 294918: case 3833865: case 3244042: case 2654219: case 2064396: case 1474573: 
                    case 884750: case 294927: case 3833874: case 3244051: case 2654228: case 2064405: 
                    case 1474582: case 884759: case 294936: case 3833883: case 3244060: case 2654237: 
                    case 2064414: case 1474591: case 884768: case 294945: case 3833892: case 3244069: 
                    case 2654246: case 2064423: case 1474600: case 884777: case 294954: case 3833901: 
                    case 3244078: case 2654255: case 2064432: case 1474609: case 884786: case 294963: 
                    case 3833910: case 3244087: case 2654264: case 2064441: case 1474618: case 884795: 
                        tenemy_mask3 += 1024; continue;
                    case 3899392: case 3309569: case 2719746: case 2129923: case 1540100: case 950277: 
                    case 360454: case 3899401: case 3309578: case 2719755: case 2129932: case 1540109: 
                    case 950286: case 360463: case 3899410: case 3309587: case 2719764: case 2129941: 
                    case 1540118: case 950295: case 360472: case 3899419: case 3309596: case 2719773: 
                    case 2129950: case 1540127: case 950304: case 360481: case 3899428: case 3309605: 
                    case 2719782: case 2129959: case 1540136: case 950313: case 360490: case 3899437: 
                    case 3309614: case 2719791: case 2129968: case 1540145: case 950322: case 360499: 
                    case 3899446: case 3309623: case 2719800: case 2129977: case 1540154: case 950331: 
                        tenemy_mask3 += 2048; continue;
                    case 3375105: case 2785282: case 2195459: case 1605636: case 1015813: case 425990: 
                    case 3375114: case 2785291: case 2195468: case 1605645: case 1015822: case 425999: 
                    case 3375123: case 2785300: case 2195477: case 1605654: case 1015831: case 426008: 
                    case 3375132: case 2785309: case 2195486: case 1605663: case 1015840: case 426017: 
                    case 3375141: case 2785318: case 2195495: case 1605672: case 1015849: case 426026: 
                    case 3375150: case 2785327: case 2195504: case 1605681: case 1015858: case 426035: 
                    case 3375159: case 2785336: case 2195513: case 1605690: case 1015867: 
                        tenemy_mask3 += 4096; continue;
                    case 3440641: case 2850818: case 2260995: case 1671172: case 1081349: case 491526: 
                    case 3440650: case 2850827: case 2261004: case 1671181: case 1081358: case 491535: 
                    case 3440659: case 2850836: case 2261013: case 1671190: case 1081367: case 491544: 
                    case 3440668: case 2850845: case 2261022: case 1671199: case 1081376: case 491553: 
                    case 3440677: case 2850854: case 2261031: case 1671208: case 1081385: case 491562: 
                    case 3440686: case 2850863: case 2261040: case 1671217: case 1081394: case 491571: 
                    case 3440695: case 2850872: case 2261049: case 1671226: case 1081403: 
                        tenemy_mask3 += 8192; continue;
                    case 3506177: case 2916354: case 2326531: case 1736708: case 1146885: case 557062: 
                    case 3506186: case 2916363: case 2326540: case 1736717: case 1146894: case 557071: 
                    case 3506195: case 2916372: case 2326549: case 1736726: case 1146903: case 557080: 
                    case 3506204: case 2916381: case 2326558: case 1736735: case 1146912: case 557089: 
                    case 3506213: case 2916390: case 2326567: case 1736744: case 1146921: case 557098: 
                    case 3506222: case 2916399: case 2326576: case 1736753: case 1146930: case 557107: 
                    case 3506231: case 2916408: case 2326585: case 1736762: case 1146939: 
                        tenemy_mask3 += 16384; continue;
                    case 3571713: case 2981890: case 2392067: case 1802244: case 1212421: case 622598: 
                    case 32775: case 3571722: case 2981899: case 2392076: case 1802253: case 1212430: 
                    case 622607: case 32784: case 3571731: case 2981908: case 2392085: case 1802262: 
                    case 1212439: case 622616: case 32793: case 3571740: case 2981917: case 2392094: 
                    case 1802271: case 1212448: case 622625: case 32802: case 3571749: case 2981926: 
                    case 2392103: case 1802280: case 1212457: case 622634: case 32811: case 3571758: 
                    case 2981935: case 2392112: case 1802289: case 1212466: case 622643: case 32820: 
                    case 3571767: case 2981944: case 2392121: case 1802298: case 1212475: 
                        tenemy_mask3 += 16384;
                        tenemy_mask3 += 16384;
                        continue;
                    case 3637249: case 3047426: case 2457603: case 1867780: case 1277957: case 688134: 
                    case 98311: case 3637258: case 3047435: case 2457612: case 1867789: case 1277966: 
                    case 688143: case 98320: case 3637267: case 3047444: case 2457621: case 1867798: 
                    case 1277975: case 688152: case 98329: case 3637276: case 3047453: case 2457630: 
                    case 1867807: case 1277984: case 688161: case 98338: case 3637285: case 3047462: 
                    case 2457639: case 1867816: case 1277993: case 688170: case 98347: case 3637294: 
                    case 3047471: case 2457648: case 1867825: case 1278002: case 688179: case 98356: 
                    case 3637303: case 3047480: case 2457657: case 1867834: case 1278011: 
                        tenemy_mask4 += 1; continue;
                    case 3702785: case 3112962: case 2523139: case 1933316: case 1343493: case 753670: 
                    case 163847: case 3702794: case 3112971: case 2523148: case 1933325: case 1343502: 
                    case 753679: case 163856: case 3702803: case 3112980: case 2523157: case 1933334: 
                    case 1343511: case 753688: case 163865: case 3702812: case 3112989: case 2523166: 
                    case 1933343: case 1343520: case 753697: case 163874: case 3702821: case 3112998: 
                    case 2523175: case 1933352: case 1343529: case 753706: case 163883: case 3702830: 
                    case 3113007: case 2523184: case 1933361: case 1343538: case 753715: case 163892: 
                    case 3702839: case 3113016: case 2523193: case 1933370: case 1343547: 
                        tenemy_mask4 += 2; continue;
                    case 3768321: case 3178498: case 2588675: case 1998852: case 1409029: case 819206: 
                    case 229383: case 3768330: case 3178507: case 2588684: case 1998861: case 1409038: 
                    case 819215: case 229392: case 3768339: case 3178516: case 2588693: case 1998870: 
                    case 1409047: case 819224: case 229401: case 3768348: case 3178525: case 2588702: 
                    case 1998879: case 1409056: case 819233: case 229410: case 3768357: case 3178534: 
                    case 2588711: case 1998888: case 1409065: case 819242: case 229419: case 3768366: 
                    case 3178543: case 2588720: case 1998897: case 1409074: case 819251: case 229428: 
                    case 3768375: case 3178552: case 2588729: case 1998906: case 1409083: 
                        tenemy_mask4 += 4; continue;
                    case 3833857: case 3244034: case 2654211: case 2064388: case 1474565: case 884742: 
                    case 294919: case 3833866: case 3244043: case 2654220: case 2064397: case 1474574: 
                    case 884751: case 294928: case 3833875: case 3244052: case 2654229: case 2064406: 
                    case 1474583: case 884760: case 294937: case 3833884: case 3244061: case 2654238: 
                    case 2064415: case 1474592: case 884769: case 294946: case 3833893: case 3244070: 
                    case 2654247: case 2064424: case 1474601: case 884778: case 294955: case 3833902: 
                    case 3244079: case 2654256: case 2064433: case 1474610: case 884787: case 294964: 
                    case 3833911: case 3244088: case 2654265: case 2064442: case 1474619: 
                        tenemy_mask4 += 8; continue;
                    case 3899393: case 3309570: case 2719747: case 2129924: case 1540101: case 950278: 
                    case 360455: case 3899402: case 3309579: case 2719756: case 2129933: case 1540110: 
                    case 950287: case 360464: case 3899411: case 3309588: case 2719765: case 2129942: 
                    case 1540119: case 950296: case 360473: case 3899420: case 3309597: case 2719774: 
                    case 2129951: case 1540128: case 950305: case 360482: case 3899429: case 3309606: 
                    case 2719783: case 2129960: case 1540137: case 950314: case 360491: case 3899438: 
                    case 3309615: case 2719792: case 2129969: case 1540146: case 950323: case 360500: 
                    case 3899447: case 3309624: case 2719801: case 2129978: case 1540155: 
                        tenemy_mask4 += 16; continue;
                    case 3375106: case 2785283: case 2195460: case 1605637: case 1015814: case 425991: 
                    case 3375115: case 2785292: case 2195469: case 1605646: case 1015823: case 426000: 
                    case 3375124: case 2785301: case 2195478: case 1605655: case 1015832: case 426009: 
                    case 3375133: case 2785310: case 2195487: case 1605664: case 1015841: case 426018: 
                    case 3375142: case 2785319: case 2195496: case 1605673: case 1015850: case 426027: 
                    case 3375151: case 2785328: case 2195505: case 1605682: case 1015859: case 426036: 
                    case 3375160: case 2785337: case 2195514: case 1605691: 
                        tenemy_mask4 += 32; continue;
                    case 3440642: case 2850819: case 2260996: case 1671173: case 1081350: case 491527: 
                    case 3440651: case 2850828: case 2261005: case 1671182: case 1081359: case 491536: 
                    case 3440660: case 2850837: case 2261014: case 1671191: case 1081368: case 491545: 
                    case 3440669: case 2850846: case 2261023: case 1671200: case 1081377: case 491554: 
                    case 3440678: case 2850855: case 2261032: case 1671209: case 1081386: case 491563: 
                    case 3440687: case 2850864: case 2261041: case 1671218: case 1081395: case 491572: 
                    case 3440696: case 2850873: case 2261050: case 1671227: 
                        tenemy_mask4 += 64; continue;
                    case 3506178: case 2916355: case 2326532: case 1736709: case 1146886: case 557063: 
                    case 3506187: case 2916364: case 2326541: case 1736718: case 1146895: case 557072: 
                    case 3506196: case 2916373: case 2326550: case 1736727: case 1146904: case 557081: 
                    case 3506205: case 2916382: case 2326559: case 1736736: case 1146913: case 557090: 
                    case 3506214: case 2916391: case 2326568: case 1736745: case 1146922: case 557099: 
                    case 3506223: case 2916400: case 2326577: case 1736754: case 1146931: case 557108: 
                    case 3506232: case 2916409: case 2326586: case 1736763: 
                        tenemy_mask4 += 128; continue;
                    case 3571714: case 2981891: case 2392068: case 1802245: case 1212422: case 622599: 
                    case 32776: case 3571723: case 2981900: case 2392077: case 1802254: case 1212431: 
                    case 622608: case 32785: case 3571732: case 2981909: case 2392086: case 1802263: 
                    case 1212440: case 622617: case 32794: case 3571741: case 2981918: case 2392095: 
                    case 1802272: case 1212449: case 622626: case 32803: case 3571750: case 2981927: 
                    case 2392104: case 1802281: case 1212458: case 622635: case 32812: case 3571759: 
                    case 2981936: case 2392113: case 1802290: case 1212467: case 622644: case 32821: 
                    case 3571768: case 2981945: case 2392122: case 1802299: 
                        tenemy_mask4 += 256; continue;
                    case 3637250: case 3047427: case 2457604: case 1867781: case 1277958: case 688135: 
                    case 98312: case 3637259: case 3047436: case 2457613: case 1867790: case 1277967: 
                    case 688144: case 98321: case 3637268: case 3047445: case 2457622: case 1867799: 
                    case 1277976: case 688153: case 98330: case 3637277: case 3047454: case 2457631: 
                    case 1867808: case 1277985: case 688162: case 98339: case 3637286: case 3047463: 
                    case 2457640: case 1867817: case 1277994: case 688171: case 98348: case 3637295: 
                    case 3047472: case 2457649: case 1867826: case 1278003: case 688180: case 98357: 
                    case 3637304: case 3047481: case 2457658: case 1867835: 
                        tenemy_mask4 += 512; continue;
                    case 3702786: case 3112963: case 2523140: case 1933317: case 1343494: case 753671: 
                    case 163848: case 3702795: case 3112972: case 2523149: case 1933326: case 1343503: 
                    case 753680: case 163857: case 3702804: case 3112981: case 2523158: case 1933335: 
                    case 1343512: case 753689: case 163866: case 3702813: case 3112990: case 2523167: 
                    case 1933344: case 1343521: case 753698: case 163875: case 3702822: case 3112999: 
                    case 2523176: case 1933353: case 1343530: case 753707: case 163884: case 3702831: 
                    case 3113008: case 2523185: case 1933362: case 1343539: case 753716: case 163893: 
                    case 3702840: case 3113017: case 2523194: case 1933371: 
                        tenemy_mask4 += 1024; continue;
                    case 3768322: case 3178499: case 2588676: case 1998853: case 1409030: case 819207: 
                    case 229384: case 3768331: case 3178508: case 2588685: case 1998862: case 1409039: 
                    case 819216: case 229393: case 3768340: case 3178517: case 2588694: case 1998871: 
                    case 1409048: case 819225: case 229402: case 3768349: case 3178526: case 2588703: 
                    case 1998880: case 1409057: case 819234: case 229411: case 3768358: case 3178535: 
                    case 2588712: case 1998889: case 1409066: case 819243: case 229420: case 3768367: 
                    case 3178544: case 2588721: case 1998898: case 1409075: case 819252: case 229429: 
                    case 3768376: case 3178553: case 2588730: case 1998907: 
                        tenemy_mask4 += 2048; continue;
                    case 3833858: case 3244035: case 2654212: case 2064389: case 1474566: case 884743: 
                    case 294920: case 3833867: case 3244044: case 2654221: case 2064398: case 1474575: 
                    case 884752: case 294929: case 3833876: case 3244053: case 2654230: case 2064407: 
                    case 1474584: case 884761: case 294938: case 3833885: case 3244062: case 2654239: 
                    case 2064416: case 1474593: case 884770: case 294947: case 3833894: case 3244071: 
                    case 2654248: case 2064425: case 1474602: case 884779: case 294956: case 3833903: 
                    case 3244080: case 2654257: case 2064434: case 1474611: case 884788: case 294965: 
                    case 3833912: case 3244089: case 2654266: case 2064443: 
                        tenemy_mask4 += 4096; continue;
                    case 3899394: case 3309571: case 2719748: case 2129925: case 1540102: case 950279: 
                    case 360456: case 3899403: case 3309580: case 2719757: case 2129934: case 1540111: 
                    case 950288: case 360465: case 3899412: case 3309589: case 2719766: case 2129943: 
                    case 1540120: case 950297: case 360474: case 3899421: case 3309598: case 2719775: 
                    case 2129952: case 1540129: case 950306: case 360483: case 3899430: case 3309607: 
                    case 2719784: case 2129961: case 1540138: case 950315: case 360492: case 3899439: 
                    case 3309616: case 2719793: case 2129970: case 1540147: case 950324: case 360501: 
                    case 3899448: case 3309625: case 2719802: case 2129979: 
                        tenemy_mask4 += 8192; continue;
                    case 3375107: case 2785284: case 2195461: case 1605638: case 1015815: case 425992: 
                    case 3375116: case 2785293: case 2195470: case 1605647: case 1015824: case 426001: 
                    case 3375125: case 2785302: case 2195479: case 1605656: case 1015833: case 426010: 
                    case 3375134: case 2785311: case 2195488: case 1605665: case 1015842: case 426019: 
                    case 3375143: case 2785320: case 2195497: case 1605674: case 1015851: case 426028: 
                    case 3375152: case 2785329: case 2195506: case 1605683: case 1015860: case 426037: 
                    case 3375161: case 2785338: case 2195515: 
                        tenemy_mask4 += 16384; continue;
                    case 3440643: case 2850820: case 2260997: case 1671174: case 1081351: case 491528: 
                    case 3440652: case 2850829: case 2261006: case 1671183: case 1081360: case 491537: 
                    case 3440661: case 2850838: case 2261015: case 1671192: case 1081369: case 491546: 
                    case 3440670: case 2850847: case 2261024: case 1671201: case 1081378: case 491555: 
                    case 3440679: case 2850856: case 2261033: case 1671210: case 1081387: case 491564: 
                    case 3440688: case 2850865: case 2261042: case 1671219: case 1081396: case 491573: 
                    case 3440697: case 2850874: case 2261051: 
                        tenemy_mask4 += 16384;
                        tenemy_mask4 += 16384;
                        continue;
                    case 3506179: case 2916356: case 2326533: case 1736710: case 1146887: case 557064: 
                    case 3506188: case 2916365: case 2326542: case 1736719: case 1146896: case 557073: 
                    case 3506197: case 2916374: case 2326551: case 1736728: case 1146905: case 557082: 
                    case 3506206: case 2916383: case 2326560: case 1736737: case 1146914: case 557091: 
                    case 3506215: case 2916392: case 2326569: case 1736746: case 1146923: case 557100: 
                    case 3506224: case 2916401: case 2326578: case 1736755: case 1146932: case 557109: 
                    case 3506233: case 2916410: case 2326587: 
                        tenemy_mask5 += 1; continue;
                    default: continue;
                }
                case CARRIER: cc++;
                default: continue;
            }
        }
        store_enemies(
            tenemy_mask0,
            tenemy_mask1,
            tenemy_mask2,
            tenemy_mask3,
            tenemy_mask4,
            tenemy_mask5,
            cc
        );
    }

    void store_enemies(
        int tenemy_mask0,
        int tenemy_mask1,
        int tenemy_mask2,
        int tenemy_mask3,
        int tenemy_mask4,
        int tenemy_mask5,
        int cc
    ) throws GameActionException {
        MapLocation myloc = rc.getLocation();
        long lenemy_mask0 = tenemy_mask0;
        long lenemy_mask1 = tenemy_mask1;
        long lenemy_mask2 = tenemy_mask2;
        long lenemy_mask3 = tenemy_mask3;
        long lenemy_mask4 = tenemy_mask4;
        long lenemy_mask5 = tenemy_mask5;
        outer: switch (myloc.y) {
            case 0: case 9: case 18: case 27: case 36: case 45: case 54: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask0 << 60) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27) | (lenemy_mask4 << 43) | (lenemy_mask5 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 4) | (lenemy_mask1 << 12) | (lenemy_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask0 << 61) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28) | (lenemy_mask4 << 44) | (lenemy_mask5 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 3) | (lenemy_mask1 << 13) | (lenemy_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask0 << 62) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29) | (lenemy_mask4 << 45) | (lenemy_mask5 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 2) | (lenemy_mask1 << 14) | (lenemy_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask0 << 63) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30) | (lenemy_mask4 << 46) | (lenemy_mask5 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 1) | (lenemy_mask1 << 15) | (lenemy_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask2 << 15) | (lenemy_mask3 << 31) | (lenemy_mask4 << 47) | (lenemy_mask5 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0) | (lenemy_mask1 << 16) | (lenemy_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask2 << 16) | (lenemy_mask3 << 32) | (lenemy_mask4 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 1) | (lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask5)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask2 << 17) | (lenemy_mask3 << 33) | (lenemy_mask4 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 2) | (lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask4 >>> 15) | (lenemy_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask2 << 18) | (lenemy_mask3 << 34) | (lenemy_mask4 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 3) | (lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask4 >>> 14) | (lenemy_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask2 << 19) | (lenemy_mask3 << 35) | (lenemy_mask4 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 4) | (lenemy_mask1 << 20) | (lenemy_mask4 >>> 13) | (lenemy_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask2 << 20) | (lenemy_mask3 << 36) | (lenemy_mask4 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask4 >>> 12) | (lenemy_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask1 << 5) | (lenemy_mask2 << 21) | (lenemy_mask3 << 37) | (lenemy_mask4 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask4 >>> 11) | (lenemy_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask1 << 6) | (lenemy_mask2 << 22) | (lenemy_mask3 << 38) | (lenemy_mask4 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask4 >>> 10) | (lenemy_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask1 << 7) | (lenemy_mask2 << 23) | (lenemy_mask3 << 39) | (lenemy_mask4 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask4 >>> 9) | (lenemy_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask1 << 8) | (lenemy_mask2 << 24) | (lenemy_mask3 << 40) | (lenemy_mask4 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask4 >>> 8) | (lenemy_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask1 << 9) | (lenemy_mask2 << 25) | (lenemy_mask3 << 41) | (lenemy_mask4 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask4 >>> 7) | (lenemy_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask1 << 10) | (lenemy_mask2 << 26) | (lenemy_mask3 << 42) | (lenemy_mask4 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask4 >>> 6) | (lenemy_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask1 << 11) | (lenemy_mask2 << 27) | (lenemy_mask3 << 43) | (lenemy_mask4 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask4 >>> 5) | (lenemy_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask1 << 12) | (lenemy_mask2 << 28) | (lenemy_mask3 << 44) | (lenemy_mask4 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask4 >>> 4) | (lenemy_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask1 << 13) | (lenemy_mask2 << 29) | (lenemy_mask3 << 45) | (lenemy_mask4 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask4 >>> 3) | (lenemy_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask1 << 14) | (lenemy_mask2 << 30) | (lenemy_mask3 << 46) | (lenemy_mask4 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask4 >>> 2) | (lenemy_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask1 << 15) | (lenemy_mask2 << 31) | (lenemy_mask3 << 47) | (lenemy_mask4 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask4 >>> 1) | (lenemy_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask1 << 16) | (lenemy_mask2 << 32) | (lenemy_mask3 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask4) | (lenemy_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask3 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask3 >>> 15) | (lenemy_mask4 << 1) | (lenemy_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask3 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask3 >>> 14) | (lenemy_mask4 << 2) | (lenemy_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask3 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 20) | (lenemy_mask3 >>> 13) | (lenemy_mask4 << 3) | (lenemy_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask1 << 20) | (lenemy_mask2 << 36) | (lenemy_mask3 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 21) | (lenemy_mask3 >>> 12) | (lenemy_mask4 << 4) | (lenemy_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask2 << 37) | (lenemy_mask3 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 22) | (lenemy_mask3 >>> 11) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask2 << 38) | (lenemy_mask3 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 23) | (lenemy_mask3 >>> 10) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask2 << 39) | (lenemy_mask3 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 24) | (lenemy_mask3 >>> 9) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask2 << 40) | (lenemy_mask3 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 25) | (lenemy_mask3 >>> 8) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask2 << 41) | (lenemy_mask3 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 26) | (lenemy_mask3 >>> 7) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask2 << 42) | (lenemy_mask3 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 27) | (lenemy_mask3 >>> 6) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask2 << 43) | (lenemy_mask3 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 28) | (lenemy_mask3 >>> 5) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask2 << 44) | (lenemy_mask3 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 29) | (lenemy_mask3 >>> 4) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask2 << 45) | (lenemy_mask3 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 30) | (lenemy_mask3 >>> 3) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask2 << 46) | (lenemy_mask3 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 31) | (lenemy_mask3 >>> 2) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask2 << 47) | (lenemy_mask3 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 32) | (lenemy_mask3 >>> 1) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask2 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 33) | (lenemy_mask3) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask2 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 34) | (lenemy_mask2 >>> 15) | (lenemy_mask3 << 1) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask2 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 35) | (lenemy_mask2 >>> 14) | (lenemy_mask3 << 2) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask2 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 13) | (lenemy_mask3 << 3) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask0 << 20) | (lenemy_mask1 << 36) | (lenemy_mask2 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 12) | (lenemy_mask3 << 4) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask0 << 21) | (lenemy_mask1 << 37) | (lenemy_mask2 << 53) | (lenemy_mask5 << 20)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 11) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask0 << 22) | (lenemy_mask1 << 38) | (lenemy_mask2 << 54) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 10) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask0 << 23) | (lenemy_mask1 << 39) | (lenemy_mask2 << 55) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 9) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask0 << 24) | (lenemy_mask1 << 40) | (lenemy_mask2 << 56) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 8) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask0 << 25) | (lenemy_mask1 << 41) | (lenemy_mask2 << 57) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 7) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask0 << 26) | (lenemy_mask1 << 42) | (lenemy_mask2 << 58) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 6) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask0 << 27) | (lenemy_mask1 << 43) | (lenemy_mask2 << 59) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 5) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask0 << 28) | (lenemy_mask1 << 44) | (lenemy_mask2 << 60) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 4) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask0 << 29) | (lenemy_mask1 << 45) | (lenemy_mask2 << 61) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 3) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask0 << 30) | (lenemy_mask1 << 46) | (lenemy_mask2 << 62) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 2) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask0 << 31) | (lenemy_mask1 << 47) | (lenemy_mask2 << 63) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 1) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask0 << 32) | (lenemy_mask1 << 48) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask0 << 33) | (lenemy_mask1 << 49) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 15) | (lenemy_mask2 << 1) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask0 << 34) | (lenemy_mask1 << 50) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 14) | (lenemy_mask2 << 2) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask0 << 35) | (lenemy_mask1 << 51) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 13) | (lenemy_mask2 << 3) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask0 << 36) | (lenemy_mask1 << 52) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 12) | (lenemy_mask2 << 4) | (lenemy_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask0 << 37) | (lenemy_mask1 << 53) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 11) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask0 << 38) | (lenemy_mask1 << 54) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21) | (lenemy_mask5 << 37)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 10) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 1: case 10: case 19: case 28: case 37: case 46: case 55: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask2 << 20) | (lenemy_mask3 << 36) | (lenemy_mask4 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask4 >>> 12) | (lenemy_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask1 << 5) | (lenemy_mask2 << 21) | (lenemy_mask3 << 37) | (lenemy_mask4 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask4 >>> 11) | (lenemy_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask1 << 6) | (lenemy_mask2 << 22) | (lenemy_mask3 << 38) | (lenemy_mask4 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask4 >>> 10) | (lenemy_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask1 << 7) | (lenemy_mask2 << 23) | (lenemy_mask3 << 39) | (lenemy_mask4 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask4 >>> 9) | (lenemy_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask1 << 8) | (lenemy_mask2 << 24) | (lenemy_mask3 << 40) | (lenemy_mask4 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask4 >>> 8) | (lenemy_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask1 << 9) | (lenemy_mask2 << 25) | (lenemy_mask3 << 41) | (lenemy_mask4 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask4 >>> 7) | (lenemy_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask1 << 10) | (lenemy_mask2 << 26) | (lenemy_mask3 << 42) | (lenemy_mask4 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask4 >>> 6) | (lenemy_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask1 << 11) | (lenemy_mask2 << 27) | (lenemy_mask3 << 43) | (lenemy_mask4 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask4 >>> 5) | (lenemy_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask1 << 12) | (lenemy_mask2 << 28) | (lenemy_mask3 << 44) | (lenemy_mask4 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask4 >>> 4) | (lenemy_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask1 << 13) | (lenemy_mask2 << 29) | (lenemy_mask3 << 45) | (lenemy_mask4 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask4 >>> 3) | (lenemy_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask1 << 14) | (lenemy_mask2 << 30) | (lenemy_mask3 << 46) | (lenemy_mask4 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask4 >>> 2) | (lenemy_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask1 << 15) | (lenemy_mask2 << 31) | (lenemy_mask3 << 47) | (lenemy_mask4 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask4 >>> 1) | (lenemy_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask1 << 16) | (lenemy_mask2 << 32) | (lenemy_mask3 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask4) | (lenemy_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask3 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask3 >>> 15) | (lenemy_mask4 << 1) | (lenemy_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask3 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask3 >>> 14) | (lenemy_mask4 << 2) | (lenemy_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask3 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 20) | (lenemy_mask3 >>> 13) | (lenemy_mask4 << 3) | (lenemy_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask1 << 20) | (lenemy_mask2 << 36) | (lenemy_mask3 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 21) | (lenemy_mask3 >>> 12) | (lenemy_mask4 << 4) | (lenemy_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask2 << 37) | (lenemy_mask3 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 22) | (lenemy_mask3 >>> 11) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask2 << 38) | (lenemy_mask3 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 23) | (lenemy_mask3 >>> 10) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask2 << 39) | (lenemy_mask3 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 24) | (lenemy_mask3 >>> 9) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask2 << 40) | (lenemy_mask3 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 25) | (lenemy_mask3 >>> 8) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask2 << 41) | (lenemy_mask3 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 26) | (lenemy_mask3 >>> 7) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask2 << 42) | (lenemy_mask3 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 27) | (lenemy_mask3 >>> 6) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask2 << 43) | (lenemy_mask3 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 28) | (lenemy_mask3 >>> 5) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask2 << 44) | (lenemy_mask3 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 29) | (lenemy_mask3 >>> 4) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask2 << 45) | (lenemy_mask3 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 30) | (lenemy_mask3 >>> 3) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask2 << 46) | (lenemy_mask3 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 31) | (lenemy_mask3 >>> 2) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask2 << 47) | (lenemy_mask3 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 32) | (lenemy_mask3 >>> 1) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask2 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 33) | (lenemy_mask3) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask2 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 34) | (lenemy_mask2 >>> 15) | (lenemy_mask3 << 1) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask2 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 35) | (lenemy_mask2 >>> 14) | (lenemy_mask3 << 2) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask2 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 13) | (lenemy_mask3 << 3) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask0 << 20) | (lenemy_mask1 << 36) | (lenemy_mask2 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 12) | (lenemy_mask3 << 4) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask0 << 21) | (lenemy_mask1 << 37) | (lenemy_mask2 << 53) | (lenemy_mask5 << 20)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 11) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask0 << 22) | (lenemy_mask1 << 38) | (lenemy_mask2 << 54) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 10) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask0 << 23) | (lenemy_mask1 << 39) | (lenemy_mask2 << 55) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 9) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask0 << 24) | (lenemy_mask1 << 40) | (lenemy_mask2 << 56) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 8) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask0 << 25) | (lenemy_mask1 << 41) | (lenemy_mask2 << 57) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 7) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask0 << 26) | (lenemy_mask1 << 42) | (lenemy_mask2 << 58) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 6) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask0 << 27) | (lenemy_mask1 << 43) | (lenemy_mask2 << 59) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 5) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask0 << 28) | (lenemy_mask1 << 44) | (lenemy_mask2 << 60) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 4) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask0 << 29) | (lenemy_mask1 << 45) | (lenemy_mask2 << 61) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 3) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask0 << 30) | (lenemy_mask1 << 46) | (lenemy_mask2 << 62) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 2) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask0 << 31) | (lenemy_mask1 << 47) | (lenemy_mask2 << 63) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 1) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask0 << 32) | (lenemy_mask1 << 48) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask0 << 33) | (lenemy_mask1 << 49) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 15) | (lenemy_mask2 << 1) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask0 << 34) | (lenemy_mask1 << 50) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 14) | (lenemy_mask2 << 2) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask0 << 35) | (lenemy_mask1 << 51) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 13) | (lenemy_mask2 << 3) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask0 << 36) | (lenemy_mask1 << 52) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 12) | (lenemy_mask2 << 4) | (lenemy_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask0 << 37) | (lenemy_mask1 << 53) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 11) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask0 << 38) | (lenemy_mask1 << 54) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21) | (lenemy_mask5 << 37)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 10) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask0 << 39) | (lenemy_mask1 << 55) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22) | (lenemy_mask5 << 38)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 9) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask0 << 40) | (lenemy_mask1 << 56) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23) | (lenemy_mask5 << 39)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 8) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask0 << 41) | (lenemy_mask1 << 57) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24) | (lenemy_mask5 << 40)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 7) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask0 << 42) | (lenemy_mask1 << 58) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25) | (lenemy_mask5 << 41)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 6) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask0 << 43) | (lenemy_mask1 << 59) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26) | (lenemy_mask5 << 42)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 5) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask0 << 44) | (lenemy_mask1 << 60) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27) | (lenemy_mask5 << 43)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 4) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask0 << 45) | (lenemy_mask1 << 61) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28) | (lenemy_mask5 << 44)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 3) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask0 << 46) | (lenemy_mask1 << 62) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29) | (lenemy_mask5 << 45)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 2) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask0 << 47) | (lenemy_mask1 << 63) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30) | (lenemy_mask5 << 46)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 1) | (lenemy_mask2 << 15) | (lenemy_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 2: case 11: case 20: case 29: case 38: case 47: case 56: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask1 << 13) | (lenemy_mask2 << 29) | (lenemy_mask3 << 45) | (lenemy_mask4 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask4 >>> 3) | (lenemy_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask1 << 14) | (lenemy_mask2 << 30) | (lenemy_mask3 << 46) | (lenemy_mask4 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask4 >>> 2) | (lenemy_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask1 << 15) | (lenemy_mask2 << 31) | (lenemy_mask3 << 47) | (lenemy_mask4 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask4 >>> 1) | (lenemy_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask1 << 16) | (lenemy_mask2 << 32) | (lenemy_mask3 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask4) | (lenemy_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask3 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask3 >>> 15) | (lenemy_mask4 << 1) | (lenemy_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask3 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask3 >>> 14) | (lenemy_mask4 << 2) | (lenemy_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask3 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 20) | (lenemy_mask3 >>> 13) | (lenemy_mask4 << 3) | (lenemy_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask1 << 20) | (lenemy_mask2 << 36) | (lenemy_mask3 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 21) | (lenemy_mask3 >>> 12) | (lenemy_mask4 << 4) | (lenemy_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask2 << 37) | (lenemy_mask3 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 22) | (lenemy_mask3 >>> 11) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask2 << 38) | (lenemy_mask3 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 23) | (lenemy_mask3 >>> 10) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask2 << 39) | (lenemy_mask3 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 24) | (lenemy_mask3 >>> 9) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask2 << 40) | (lenemy_mask3 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 25) | (lenemy_mask3 >>> 8) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask2 << 41) | (lenemy_mask3 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 26) | (lenemy_mask3 >>> 7) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask2 << 42) | (lenemy_mask3 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 27) | (lenemy_mask3 >>> 6) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask2 << 43) | (lenemy_mask3 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 28) | (lenemy_mask3 >>> 5) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask2 << 44) | (lenemy_mask3 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 29) | (lenemy_mask3 >>> 4) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask2 << 45) | (lenemy_mask3 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 30) | (lenemy_mask3 >>> 3) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask2 << 46) | (lenemy_mask3 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 31) | (lenemy_mask3 >>> 2) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask2 << 47) | (lenemy_mask3 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 32) | (lenemy_mask3 >>> 1) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask2 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 33) | (lenemy_mask3) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask2 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 34) | (lenemy_mask2 >>> 15) | (lenemy_mask3 << 1) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask2 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 35) | (lenemy_mask2 >>> 14) | (lenemy_mask3 << 2) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask2 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 13) | (lenemy_mask3 << 3) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask0 << 20) | (lenemy_mask1 << 36) | (lenemy_mask2 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 12) | (lenemy_mask3 << 4) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask0 << 21) | (lenemy_mask1 << 37) | (lenemy_mask2 << 53) | (lenemy_mask5 << 20)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 11) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask0 << 22) | (lenemy_mask1 << 38) | (lenemy_mask2 << 54) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 10) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask0 << 23) | (lenemy_mask1 << 39) | (lenemy_mask2 << 55) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 9) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask0 << 24) | (lenemy_mask1 << 40) | (lenemy_mask2 << 56) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 8) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask0 << 25) | (lenemy_mask1 << 41) | (lenemy_mask2 << 57) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 7) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask0 << 26) | (lenemy_mask1 << 42) | (lenemy_mask2 << 58) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 6) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask0 << 27) | (lenemy_mask1 << 43) | (lenemy_mask2 << 59) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 5) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask0 << 28) | (lenemy_mask1 << 44) | (lenemy_mask2 << 60) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 4) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask0 << 29) | (lenemy_mask1 << 45) | (lenemy_mask2 << 61) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 3) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask0 << 30) | (lenemy_mask1 << 46) | (lenemy_mask2 << 62) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 2) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask0 << 31) | (lenemy_mask1 << 47) | (lenemy_mask2 << 63) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 1) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask0 << 32) | (lenemy_mask1 << 48) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask0 << 33) | (lenemy_mask1 << 49) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 15) | (lenemy_mask2 << 1) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask0 << 34) | (lenemy_mask1 << 50) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 14) | (lenemy_mask2 << 2) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask0 << 35) | (lenemy_mask1 << 51) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 13) | (lenemy_mask2 << 3) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask0 << 36) | (lenemy_mask1 << 52) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 12) | (lenemy_mask2 << 4) | (lenemy_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask0 << 37) | (lenemy_mask1 << 53) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 11) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask0 << 38) | (lenemy_mask1 << 54) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21) | (lenemy_mask5 << 37)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 10) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask0 << 39) | (lenemy_mask1 << 55) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22) | (lenemy_mask5 << 38)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 9) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask0 << 40) | (lenemy_mask1 << 56) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23) | (lenemy_mask5 << 39)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 8) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask0 << 41) | (lenemy_mask1 << 57) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24) | (lenemy_mask5 << 40)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 7) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask0 << 42) | (lenemy_mask1 << 58) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25) | (lenemy_mask5 << 41)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 6) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask0 << 43) | (lenemy_mask1 << 59) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26) | (lenemy_mask5 << 42)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 5) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask0 << 44) | (lenemy_mask1 << 60) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27) | (lenemy_mask5 << 43)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 4) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask0 << 45) | (lenemy_mask1 << 61) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28) | (lenemy_mask5 << 44)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 3) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask0 << 46) | (lenemy_mask1 << 62) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29) | (lenemy_mask5 << 45)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 2) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask0 << 47) | (lenemy_mask1 << 63) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30) | (lenemy_mask5 << 46)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 1) | (lenemy_mask2 << 15) | (lenemy_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask0 << 48) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31) | (lenemy_mask5 << 47)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1) | (lenemy_mask2 << 16) | (lenemy_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask0 << 49) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32) | (lenemy_mask5 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 15) | (lenemy_mask1 << 1) | (lenemy_mask2 << 17) | (lenemy_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask0 << 50) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33) | (lenemy_mask5 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 14) | (lenemy_mask1 << 2) | (lenemy_mask2 << 18) | (lenemy_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask0 << 51) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34) | (lenemy_mask5 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 13) | (lenemy_mask1 << 3) | (lenemy_mask2 << 19) | (lenemy_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask0 << 52) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35) | (lenemy_mask5 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 12) | (lenemy_mask1 << 4) | (lenemy_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask0 << 53) | (lenemy_mask3 << 20) | (lenemy_mask4 << 36) | (lenemy_mask5 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 11) | (lenemy_mask1 << 5) | (lenemy_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask0 << 54) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21) | (lenemy_mask4 << 37) | (lenemy_mask5 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 10) | (lenemy_mask1 << 6) | (lenemy_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask0 << 55) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22) | (lenemy_mask4 << 38) | (lenemy_mask5 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 9) | (lenemy_mask1 << 7) | (lenemy_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask0 << 56) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23) | (lenemy_mask4 << 39) | (lenemy_mask5 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 8) | (lenemy_mask1 << 8) | (lenemy_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 3: case 12: case 21: case 30: case 39: case 48: case 57: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask2 << 38) | (lenemy_mask3 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 23) | (lenemy_mask3 >>> 10) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask2 << 39) | (lenemy_mask3 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 24) | (lenemy_mask3 >>> 9) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask2 << 40) | (lenemy_mask3 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 25) | (lenemy_mask3 >>> 8) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask2 << 41) | (lenemy_mask3 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 26) | (lenemy_mask3 >>> 7) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask2 << 42) | (lenemy_mask3 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 27) | (lenemy_mask3 >>> 6) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask2 << 43) | (lenemy_mask3 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 28) | (lenemy_mask3 >>> 5) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask2 << 44) | (lenemy_mask3 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 29) | (lenemy_mask3 >>> 4) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask2 << 45) | (lenemy_mask3 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 30) | (lenemy_mask3 >>> 3) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask2 << 46) | (lenemy_mask3 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 31) | (lenemy_mask3 >>> 2) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask2 << 47) | (lenemy_mask3 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 32) | (lenemy_mask3 >>> 1) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask2 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 33) | (lenemy_mask3) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask2 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 34) | (lenemy_mask2 >>> 15) | (lenemy_mask3 << 1) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask2 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 35) | (lenemy_mask2 >>> 14) | (lenemy_mask3 << 2) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask2 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 13) | (lenemy_mask3 << 3) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask0 << 20) | (lenemy_mask1 << 36) | (lenemy_mask2 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 12) | (lenemy_mask3 << 4) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask0 << 21) | (lenemy_mask1 << 37) | (lenemy_mask2 << 53) | (lenemy_mask5 << 20)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 11) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask0 << 22) | (lenemy_mask1 << 38) | (lenemy_mask2 << 54) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 10) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask0 << 23) | (lenemy_mask1 << 39) | (lenemy_mask2 << 55) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 9) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask0 << 24) | (lenemy_mask1 << 40) | (lenemy_mask2 << 56) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 8) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask0 << 25) | (lenemy_mask1 << 41) | (lenemy_mask2 << 57) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 7) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask0 << 26) | (lenemy_mask1 << 42) | (lenemy_mask2 << 58) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 6) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask0 << 27) | (lenemy_mask1 << 43) | (lenemy_mask2 << 59) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 5) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask0 << 28) | (lenemy_mask1 << 44) | (lenemy_mask2 << 60) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 4) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask0 << 29) | (lenemy_mask1 << 45) | (lenemy_mask2 << 61) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 3) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask0 << 30) | (lenemy_mask1 << 46) | (lenemy_mask2 << 62) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 2) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask0 << 31) | (lenemy_mask1 << 47) | (lenemy_mask2 << 63) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 1) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask0 << 32) | (lenemy_mask1 << 48) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask0 << 33) | (lenemy_mask1 << 49) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 15) | (lenemy_mask2 << 1) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask0 << 34) | (lenemy_mask1 << 50) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 14) | (lenemy_mask2 << 2) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask0 << 35) | (lenemy_mask1 << 51) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 13) | (lenemy_mask2 << 3) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask0 << 36) | (lenemy_mask1 << 52) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 12) | (lenemy_mask2 << 4) | (lenemy_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask0 << 37) | (lenemy_mask1 << 53) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 11) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask0 << 38) | (lenemy_mask1 << 54) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21) | (lenemy_mask5 << 37)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 10) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask0 << 39) | (lenemy_mask1 << 55) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22) | (lenemy_mask5 << 38)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 9) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask0 << 40) | (lenemy_mask1 << 56) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23) | (lenemy_mask5 << 39)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 8) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask0 << 41) | (lenemy_mask1 << 57) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24) | (lenemy_mask5 << 40)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 7) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask0 << 42) | (lenemy_mask1 << 58) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25) | (lenemy_mask5 << 41)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 6) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask0 << 43) | (lenemy_mask1 << 59) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26) | (lenemy_mask5 << 42)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 5) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask0 << 44) | (lenemy_mask1 << 60) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27) | (lenemy_mask5 << 43)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 4) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask0 << 45) | (lenemy_mask1 << 61) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28) | (lenemy_mask5 << 44)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 3) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask0 << 46) | (lenemy_mask1 << 62) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29) | (lenemy_mask5 << 45)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 2) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask0 << 47) | (lenemy_mask1 << 63) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30) | (lenemy_mask5 << 46)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 1) | (lenemy_mask2 << 15) | (lenemy_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask0 << 48) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31) | (lenemy_mask5 << 47)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1) | (lenemy_mask2 << 16) | (lenemy_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask0 << 49) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32) | (lenemy_mask5 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 15) | (lenemy_mask1 << 1) | (lenemy_mask2 << 17) | (lenemy_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask0 << 50) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33) | (lenemy_mask5 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 14) | (lenemy_mask1 << 2) | (lenemy_mask2 << 18) | (lenemy_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask0 << 51) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34) | (lenemy_mask5 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 13) | (lenemy_mask1 << 3) | (lenemy_mask2 << 19) | (lenemy_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask0 << 52) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35) | (lenemy_mask5 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 12) | (lenemy_mask1 << 4) | (lenemy_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask0 << 53) | (lenemy_mask3 << 20) | (lenemy_mask4 << 36) | (lenemy_mask5 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 11) | (lenemy_mask1 << 5) | (lenemy_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask0 << 54) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21) | (lenemy_mask4 << 37) | (lenemy_mask5 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 10) | (lenemy_mask1 << 6) | (lenemy_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask0 << 55) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22) | (lenemy_mask4 << 38) | (lenemy_mask5 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 9) | (lenemy_mask1 << 7) | (lenemy_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask0 << 56) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23) | (lenemy_mask4 << 39) | (lenemy_mask5 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 8) | (lenemy_mask1 << 8) | (lenemy_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask0 << 57) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24) | (lenemy_mask4 << 40) | (lenemy_mask5 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 7) | (lenemy_mask1 << 9) | (lenemy_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask0 << 58) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25) | (lenemy_mask4 << 41) | (lenemy_mask5 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 6) | (lenemy_mask1 << 10) | (lenemy_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask0 << 59) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26) | (lenemy_mask4 << 42) | (lenemy_mask5 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 5) | (lenemy_mask1 << 11) | (lenemy_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask0 << 60) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27) | (lenemy_mask4 << 43) | (lenemy_mask5 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 4) | (lenemy_mask1 << 12) | (lenemy_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask0 << 61) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28) | (lenemy_mask4 << 44) | (lenemy_mask5 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 3) | (lenemy_mask1 << 13) | (lenemy_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask0 << 62) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29) | (lenemy_mask4 << 45) | (lenemy_mask5 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 2) | (lenemy_mask1 << 14) | (lenemy_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask0 << 63) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30) | (lenemy_mask4 << 46) | (lenemy_mask5 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 1) | (lenemy_mask1 << 15) | (lenemy_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask2 << 15) | (lenemy_mask3 << 31) | (lenemy_mask4 << 47) | (lenemy_mask5 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0) | (lenemy_mask1 << 16) | (lenemy_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask2 << 16) | (lenemy_mask3 << 32) | (lenemy_mask4 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 1) | (lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask5)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 4: case 13: case 22: case 31: case 40: case 49: case 58: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask2 << 47) | (lenemy_mask3 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 32) | (lenemy_mask3 >>> 1) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask2 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 33) | (lenemy_mask3) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask2 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 34) | (lenemy_mask2 >>> 15) | (lenemy_mask3 << 1) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask2 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 35) | (lenemy_mask2 >>> 14) | (lenemy_mask3 << 2) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask2 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 13) | (lenemy_mask3 << 3) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask0 << 20) | (lenemy_mask1 << 36) | (lenemy_mask2 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 12) | (lenemy_mask3 << 4) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask0 << 21) | (lenemy_mask1 << 37) | (lenemy_mask2 << 53) | (lenemy_mask5 << 20)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 11) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask0 << 22) | (lenemy_mask1 << 38) | (lenemy_mask2 << 54) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 10) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask0 << 23) | (lenemy_mask1 << 39) | (lenemy_mask2 << 55) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 9) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask0 << 24) | (lenemy_mask1 << 40) | (lenemy_mask2 << 56) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 8) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask0 << 25) | (lenemy_mask1 << 41) | (lenemy_mask2 << 57) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 7) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask0 << 26) | (lenemy_mask1 << 42) | (lenemy_mask2 << 58) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 6) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask0 << 27) | (lenemy_mask1 << 43) | (lenemy_mask2 << 59) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 5) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask0 << 28) | (lenemy_mask1 << 44) | (lenemy_mask2 << 60) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 4) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask0 << 29) | (lenemy_mask1 << 45) | (lenemy_mask2 << 61) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 3) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask0 << 30) | (lenemy_mask1 << 46) | (lenemy_mask2 << 62) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 2) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask0 << 31) | (lenemy_mask1 << 47) | (lenemy_mask2 << 63) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 1) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask0 << 32) | (lenemy_mask1 << 48) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask0 << 33) | (lenemy_mask1 << 49) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 15) | (lenemy_mask2 << 1) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask0 << 34) | (lenemy_mask1 << 50) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 14) | (lenemy_mask2 << 2) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask0 << 35) | (lenemy_mask1 << 51) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 13) | (lenemy_mask2 << 3) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask0 << 36) | (lenemy_mask1 << 52) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 12) | (lenemy_mask2 << 4) | (lenemy_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask0 << 37) | (lenemy_mask1 << 53) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 11) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask0 << 38) | (lenemy_mask1 << 54) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21) | (lenemy_mask5 << 37)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 10) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask0 << 39) | (lenemy_mask1 << 55) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22) | (lenemy_mask5 << 38)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 9) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask0 << 40) | (lenemy_mask1 << 56) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23) | (lenemy_mask5 << 39)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 8) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask0 << 41) | (lenemy_mask1 << 57) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24) | (lenemy_mask5 << 40)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 7) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask0 << 42) | (lenemy_mask1 << 58) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25) | (lenemy_mask5 << 41)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 6) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask0 << 43) | (lenemy_mask1 << 59) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26) | (lenemy_mask5 << 42)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 5) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask0 << 44) | (lenemy_mask1 << 60) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27) | (lenemy_mask5 << 43)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 4) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask0 << 45) | (lenemy_mask1 << 61) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28) | (lenemy_mask5 << 44)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 3) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask0 << 46) | (lenemy_mask1 << 62) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29) | (lenemy_mask5 << 45)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 2) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask0 << 47) | (lenemy_mask1 << 63) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30) | (lenemy_mask5 << 46)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 1) | (lenemy_mask2 << 15) | (lenemy_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask0 << 48) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31) | (lenemy_mask5 << 47)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1) | (lenemy_mask2 << 16) | (lenemy_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask0 << 49) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32) | (lenemy_mask5 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 15) | (lenemy_mask1 << 1) | (lenemy_mask2 << 17) | (lenemy_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask0 << 50) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33) | (lenemy_mask5 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 14) | (lenemy_mask1 << 2) | (lenemy_mask2 << 18) | (lenemy_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask0 << 51) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34) | (lenemy_mask5 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 13) | (lenemy_mask1 << 3) | (lenemy_mask2 << 19) | (lenemy_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask0 << 52) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35) | (lenemy_mask5 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 12) | (lenemy_mask1 << 4) | (lenemy_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask0 << 53) | (lenemy_mask3 << 20) | (lenemy_mask4 << 36) | (lenemy_mask5 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 11) | (lenemy_mask1 << 5) | (lenemy_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask0 << 54) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21) | (lenemy_mask4 << 37) | (lenemy_mask5 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 10) | (lenemy_mask1 << 6) | (lenemy_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask0 << 55) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22) | (lenemy_mask4 << 38) | (lenemy_mask5 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 9) | (lenemy_mask1 << 7) | (lenemy_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask0 << 56) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23) | (lenemy_mask4 << 39) | (lenemy_mask5 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 8) | (lenemy_mask1 << 8) | (lenemy_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask0 << 57) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24) | (lenemy_mask4 << 40) | (lenemy_mask5 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 7) | (lenemy_mask1 << 9) | (lenemy_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask0 << 58) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25) | (lenemy_mask4 << 41) | (lenemy_mask5 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 6) | (lenemy_mask1 << 10) | (lenemy_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask0 << 59) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26) | (lenemy_mask4 << 42) | (lenemy_mask5 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 5) | (lenemy_mask1 << 11) | (lenemy_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask0 << 60) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27) | (lenemy_mask4 << 43) | (lenemy_mask5 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 4) | (lenemy_mask1 << 12) | (lenemy_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask0 << 61) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28) | (lenemy_mask4 << 44) | (lenemy_mask5 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 3) | (lenemy_mask1 << 13) | (lenemy_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask0 << 62) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29) | (lenemy_mask4 << 45) | (lenemy_mask5 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 2) | (lenemy_mask1 << 14) | (lenemy_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask0 << 63) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30) | (lenemy_mask4 << 46) | (lenemy_mask5 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 1) | (lenemy_mask1 << 15) | (lenemy_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask2 << 15) | (lenemy_mask3 << 31) | (lenemy_mask4 << 47) | (lenemy_mask5 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0) | (lenemy_mask1 << 16) | (lenemy_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask2 << 16) | (lenemy_mask3 << 32) | (lenemy_mask4 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 1) | (lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask5)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask2 << 17) | (lenemy_mask3 << 33) | (lenemy_mask4 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 2) | (lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask4 >>> 15) | (lenemy_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask2 << 18) | (lenemy_mask3 << 34) | (lenemy_mask4 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 3) | (lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask4 >>> 14) | (lenemy_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask2 << 19) | (lenemy_mask3 << 35) | (lenemy_mask4 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 4) | (lenemy_mask1 << 20) | (lenemy_mask4 >>> 13) | (lenemy_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask2 << 20) | (lenemy_mask3 << 36) | (lenemy_mask4 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask4 >>> 12) | (lenemy_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask1 << 5) | (lenemy_mask2 << 21) | (lenemy_mask3 << 37) | (lenemy_mask4 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask4 >>> 11) | (lenemy_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask1 << 6) | (lenemy_mask2 << 22) | (lenemy_mask3 << 38) | (lenemy_mask4 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask4 >>> 10) | (lenemy_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask1 << 7) | (lenemy_mask2 << 23) | (lenemy_mask3 << 39) | (lenemy_mask4 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask4 >>> 9) | (lenemy_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask1 << 8) | (lenemy_mask2 << 24) | (lenemy_mask3 << 40) | (lenemy_mask4 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask4 >>> 8) | (lenemy_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask1 << 9) | (lenemy_mask2 << 25) | (lenemy_mask3 << 41) | (lenemy_mask4 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask4 >>> 7) | (lenemy_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 5: case 14: case 23: case 32: case 41: case 50: case 59: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask0 << 24) | (lenemy_mask1 << 40) | (lenemy_mask2 << 56) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 8) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask0 << 25) | (lenemy_mask1 << 41) | (lenemy_mask2 << 57) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 7) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask0 << 26) | (lenemy_mask1 << 42) | (lenemy_mask2 << 58) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 6) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask0 << 27) | (lenemy_mask1 << 43) | (lenemy_mask2 << 59) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 5) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask0 << 28) | (lenemy_mask1 << 44) | (lenemy_mask2 << 60) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 4) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask0 << 29) | (lenemy_mask1 << 45) | (lenemy_mask2 << 61) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 3) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask0 << 30) | (lenemy_mask1 << 46) | (lenemy_mask2 << 62) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 2) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask0 << 31) | (lenemy_mask1 << 47) | (lenemy_mask2 << 63) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 1) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask0 << 32) | (lenemy_mask1 << 48) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask0 << 33) | (lenemy_mask1 << 49) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 15) | (lenemy_mask2 << 1) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask0 << 34) | (lenemy_mask1 << 50) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 14) | (lenemy_mask2 << 2) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask0 << 35) | (lenemy_mask1 << 51) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 13) | (lenemy_mask2 << 3) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask0 << 36) | (lenemy_mask1 << 52) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 12) | (lenemy_mask2 << 4) | (lenemy_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask0 << 37) | (lenemy_mask1 << 53) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 11) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask0 << 38) | (lenemy_mask1 << 54) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21) | (lenemy_mask5 << 37)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 10) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask0 << 39) | (lenemy_mask1 << 55) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22) | (lenemy_mask5 << 38)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 9) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask0 << 40) | (lenemy_mask1 << 56) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23) | (lenemy_mask5 << 39)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 8) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask0 << 41) | (lenemy_mask1 << 57) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24) | (lenemy_mask5 << 40)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 7) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask0 << 42) | (lenemy_mask1 << 58) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25) | (lenemy_mask5 << 41)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 6) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask0 << 43) | (lenemy_mask1 << 59) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26) | (lenemy_mask5 << 42)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 5) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask0 << 44) | (lenemy_mask1 << 60) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27) | (lenemy_mask5 << 43)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 4) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask0 << 45) | (lenemy_mask1 << 61) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28) | (lenemy_mask5 << 44)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 3) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask0 << 46) | (lenemy_mask1 << 62) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29) | (lenemy_mask5 << 45)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 2) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask0 << 47) | (lenemy_mask1 << 63) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30) | (lenemy_mask5 << 46)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 1) | (lenemy_mask2 << 15) | (lenemy_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask0 << 48) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31) | (lenemy_mask5 << 47)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1) | (lenemy_mask2 << 16) | (lenemy_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask0 << 49) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32) | (lenemy_mask5 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 15) | (lenemy_mask1 << 1) | (lenemy_mask2 << 17) | (lenemy_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask0 << 50) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33) | (lenemy_mask5 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 14) | (lenemy_mask1 << 2) | (lenemy_mask2 << 18) | (lenemy_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask0 << 51) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34) | (lenemy_mask5 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 13) | (lenemy_mask1 << 3) | (lenemy_mask2 << 19) | (lenemy_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask0 << 52) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35) | (lenemy_mask5 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 12) | (lenemy_mask1 << 4) | (lenemy_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask0 << 53) | (lenemy_mask3 << 20) | (lenemy_mask4 << 36) | (lenemy_mask5 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 11) | (lenemy_mask1 << 5) | (lenemy_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask0 << 54) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21) | (lenemy_mask4 << 37) | (lenemy_mask5 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 10) | (lenemy_mask1 << 6) | (lenemy_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask0 << 55) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22) | (lenemy_mask4 << 38) | (lenemy_mask5 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 9) | (lenemy_mask1 << 7) | (lenemy_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask0 << 56) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23) | (lenemy_mask4 << 39) | (lenemy_mask5 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 8) | (lenemy_mask1 << 8) | (lenemy_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask0 << 57) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24) | (lenemy_mask4 << 40) | (lenemy_mask5 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 7) | (lenemy_mask1 << 9) | (lenemy_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask0 << 58) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25) | (lenemy_mask4 << 41) | (lenemy_mask5 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 6) | (lenemy_mask1 << 10) | (lenemy_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask0 << 59) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26) | (lenemy_mask4 << 42) | (lenemy_mask5 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 5) | (lenemy_mask1 << 11) | (lenemy_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask0 << 60) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27) | (lenemy_mask4 << 43) | (lenemy_mask5 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 4) | (lenemy_mask1 << 12) | (lenemy_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask0 << 61) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28) | (lenemy_mask4 << 44) | (lenemy_mask5 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 3) | (lenemy_mask1 << 13) | (lenemy_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask0 << 62) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29) | (lenemy_mask4 << 45) | (lenemy_mask5 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 2) | (lenemy_mask1 << 14) | (lenemy_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask0 << 63) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30) | (lenemy_mask4 << 46) | (lenemy_mask5 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 1) | (lenemy_mask1 << 15) | (lenemy_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask2 << 15) | (lenemy_mask3 << 31) | (lenemy_mask4 << 47) | (lenemy_mask5 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0) | (lenemy_mask1 << 16) | (lenemy_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask2 << 16) | (lenemy_mask3 << 32) | (lenemy_mask4 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 1) | (lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask5)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask2 << 17) | (lenemy_mask3 << 33) | (lenemy_mask4 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 2) | (lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask4 >>> 15) | (lenemy_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask2 << 18) | (lenemy_mask3 << 34) | (lenemy_mask4 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 3) | (lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask4 >>> 14) | (lenemy_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask2 << 19) | (lenemy_mask3 << 35) | (lenemy_mask4 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 4) | (lenemy_mask1 << 20) | (lenemy_mask4 >>> 13) | (lenemy_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask2 << 20) | (lenemy_mask3 << 36) | (lenemy_mask4 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask4 >>> 12) | (lenemy_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask1 << 5) | (lenemy_mask2 << 21) | (lenemy_mask3 << 37) | (lenemy_mask4 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask4 >>> 11) | (lenemy_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask1 << 6) | (lenemy_mask2 << 22) | (lenemy_mask3 << 38) | (lenemy_mask4 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask4 >>> 10) | (lenemy_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask1 << 7) | (lenemy_mask2 << 23) | (lenemy_mask3 << 39) | (lenemy_mask4 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask4 >>> 9) | (lenemy_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask1 << 8) | (lenemy_mask2 << 24) | (lenemy_mask3 << 40) | (lenemy_mask4 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask4 >>> 8) | (lenemy_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask1 << 9) | (lenemy_mask2 << 25) | (lenemy_mask3 << 41) | (lenemy_mask4 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask4 >>> 7) | (lenemy_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask1 << 10) | (lenemy_mask2 << 26) | (lenemy_mask3 << 42) | (lenemy_mask4 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask4 >>> 6) | (lenemy_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask1 << 11) | (lenemy_mask2 << 27) | (lenemy_mask3 << 43) | (lenemy_mask4 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask4 >>> 5) | (lenemy_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask1 << 12) | (lenemy_mask2 << 28) | (lenemy_mask3 << 44) | (lenemy_mask4 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask4 >>> 4) | (lenemy_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask1 << 13) | (lenemy_mask2 << 29) | (lenemy_mask3 << 45) | (lenemy_mask4 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask4 >>> 3) | (lenemy_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask1 << 14) | (lenemy_mask2 << 30) | (lenemy_mask3 << 46) | (lenemy_mask4 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask4 >>> 2) | (lenemy_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask1 << 15) | (lenemy_mask2 << 31) | (lenemy_mask3 << 47) | (lenemy_mask4 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask4 >>> 1) | (lenemy_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask1 << 16) | (lenemy_mask2 << 32) | (lenemy_mask3 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask4) | (lenemy_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask3 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask3 >>> 15) | (lenemy_mask4 << 1) | (lenemy_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask3 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask3 >>> 14) | (lenemy_mask4 << 2) | (lenemy_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 6: case 15: case 24: case 33: case 42: case 51: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask0 << 33) | (lenemy_mask1 << 49) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 15) | (lenemy_mask2 << 1) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask0 << 34) | (lenemy_mask1 << 50) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 14) | (lenemy_mask2 << 2) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask0 << 35) | (lenemy_mask1 << 51) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 13) | (lenemy_mask2 << 3) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask0 << 36) | (lenemy_mask1 << 52) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 12) | (lenemy_mask2 << 4) | (lenemy_mask3 << 20)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask0 << 37) | (lenemy_mask1 << 53) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 11) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask0 << 38) | (lenemy_mask1 << 54) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21) | (lenemy_mask5 << 37)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 10) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask0 << 39) | (lenemy_mask1 << 55) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22) | (lenemy_mask5 << 38)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 9) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask0 << 40) | (lenemy_mask1 << 56) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23) | (lenemy_mask5 << 39)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 8) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask0 << 41) | (lenemy_mask1 << 57) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24) | (lenemy_mask5 << 40)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 7) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask0 << 42) | (lenemy_mask1 << 58) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25) | (lenemy_mask5 << 41)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 6) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask0 << 43) | (lenemy_mask1 << 59) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26) | (lenemy_mask5 << 42)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 5) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask0 << 44) | (lenemy_mask1 << 60) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27) | (lenemy_mask5 << 43)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 4) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask0 << 45) | (lenemy_mask1 << 61) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28) | (lenemy_mask5 << 44)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 3) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask0 << 46) | (lenemy_mask1 << 62) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29) | (lenemy_mask5 << 45)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 2) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask0 << 47) | (lenemy_mask1 << 63) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30) | (lenemy_mask5 << 46)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 1) | (lenemy_mask2 << 15) | (lenemy_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask0 << 48) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31) | (lenemy_mask5 << 47)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1) | (lenemy_mask2 << 16) | (lenemy_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask0 << 49) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32) | (lenemy_mask5 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 15) | (lenemy_mask1 << 1) | (lenemy_mask2 << 17) | (lenemy_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask0 << 50) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33) | (lenemy_mask5 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 14) | (lenemy_mask1 << 2) | (lenemy_mask2 << 18) | (lenemy_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask0 << 51) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34) | (lenemy_mask5 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 13) | (lenemy_mask1 << 3) | (lenemy_mask2 << 19) | (lenemy_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask0 << 52) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35) | (lenemy_mask5 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 12) | (lenemy_mask1 << 4) | (lenemy_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask0 << 53) | (lenemy_mask3 << 20) | (lenemy_mask4 << 36) | (lenemy_mask5 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 11) | (lenemy_mask1 << 5) | (lenemy_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask0 << 54) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21) | (lenemy_mask4 << 37) | (lenemy_mask5 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 10) | (lenemy_mask1 << 6) | (lenemy_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask0 << 55) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22) | (lenemy_mask4 << 38) | (lenemy_mask5 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 9) | (lenemy_mask1 << 7) | (lenemy_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask0 << 56) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23) | (lenemy_mask4 << 39) | (lenemy_mask5 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 8) | (lenemy_mask1 << 8) | (lenemy_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask0 << 57) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24) | (lenemy_mask4 << 40) | (lenemy_mask5 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 7) | (lenemy_mask1 << 9) | (lenemy_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask0 << 58) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25) | (lenemy_mask4 << 41) | (lenemy_mask5 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 6) | (lenemy_mask1 << 10) | (lenemy_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask0 << 59) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26) | (lenemy_mask4 << 42) | (lenemy_mask5 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 5) | (lenemy_mask1 << 11) | (lenemy_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask0 << 60) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27) | (lenemy_mask4 << 43) | (lenemy_mask5 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 4) | (lenemy_mask1 << 12) | (lenemy_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask0 << 61) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28) | (lenemy_mask4 << 44) | (lenemy_mask5 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 3) | (lenemy_mask1 << 13) | (lenemy_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask0 << 62) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29) | (lenemy_mask4 << 45) | (lenemy_mask5 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 2) | (lenemy_mask1 << 14) | (lenemy_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask0 << 63) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30) | (lenemy_mask4 << 46) | (lenemy_mask5 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 1) | (lenemy_mask1 << 15) | (lenemy_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask2 << 15) | (lenemy_mask3 << 31) | (lenemy_mask4 << 47) | (lenemy_mask5 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0) | (lenemy_mask1 << 16) | (lenemy_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask2 << 16) | (lenemy_mask3 << 32) | (lenemy_mask4 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 1) | (lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask5)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask2 << 17) | (lenemy_mask3 << 33) | (lenemy_mask4 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 2) | (lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask4 >>> 15) | (lenemy_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask2 << 18) | (lenemy_mask3 << 34) | (lenemy_mask4 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 3) | (lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask4 >>> 14) | (lenemy_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask2 << 19) | (lenemy_mask3 << 35) | (lenemy_mask4 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 4) | (lenemy_mask1 << 20) | (lenemy_mask4 >>> 13) | (lenemy_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask2 << 20) | (lenemy_mask3 << 36) | (lenemy_mask4 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask4 >>> 12) | (lenemy_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask1 << 5) | (lenemy_mask2 << 21) | (lenemy_mask3 << 37) | (lenemy_mask4 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask4 >>> 11) | (lenemy_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask1 << 6) | (lenemy_mask2 << 22) | (lenemy_mask3 << 38) | (lenemy_mask4 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask4 >>> 10) | (lenemy_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask1 << 7) | (lenemy_mask2 << 23) | (lenemy_mask3 << 39) | (lenemy_mask4 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask4 >>> 9) | (lenemy_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask1 << 8) | (lenemy_mask2 << 24) | (lenemy_mask3 << 40) | (lenemy_mask4 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask4 >>> 8) | (lenemy_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask1 << 9) | (lenemy_mask2 << 25) | (lenemy_mask3 << 41) | (lenemy_mask4 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask4 >>> 7) | (lenemy_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask1 << 10) | (lenemy_mask2 << 26) | (lenemy_mask3 << 42) | (lenemy_mask4 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask4 >>> 6) | (lenemy_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask1 << 11) | (lenemy_mask2 << 27) | (lenemy_mask3 << 43) | (lenemy_mask4 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask4 >>> 5) | (lenemy_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask1 << 12) | (lenemy_mask2 << 28) | (lenemy_mask3 << 44) | (lenemy_mask4 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask4 >>> 4) | (lenemy_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask1 << 13) | (lenemy_mask2 << 29) | (lenemy_mask3 << 45) | (lenemy_mask4 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask4 >>> 3) | (lenemy_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask1 << 14) | (lenemy_mask2 << 30) | (lenemy_mask3 << 46) | (lenemy_mask4 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask4 >>> 2) | (lenemy_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask1 << 15) | (lenemy_mask2 << 31) | (lenemy_mask3 << 47) | (lenemy_mask4 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask4 >>> 1) | (lenemy_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask1 << 16) | (lenemy_mask2 << 32) | (lenemy_mask3 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask4) | (lenemy_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask3 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask3 >>> 15) | (lenemy_mask4 << 1) | (lenemy_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask3 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask3 >>> 14) | (lenemy_mask4 << 2) | (lenemy_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask3 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 20) | (lenemy_mask3 >>> 13) | (lenemy_mask4 << 3) | (lenemy_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask1 << 20) | (lenemy_mask2 << 36) | (lenemy_mask3 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 21) | (lenemy_mask3 >>> 12) | (lenemy_mask4 << 4) | (lenemy_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask2 << 37) | (lenemy_mask3 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 22) | (lenemy_mask3 >>> 11) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask2 << 38) | (lenemy_mask3 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 23) | (lenemy_mask3 >>> 10) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask2 << 39) | (lenemy_mask3 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 24) | (lenemy_mask3 >>> 9) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask2 << 40) | (lenemy_mask3 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 25) | (lenemy_mask3 >>> 8) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask2 << 41) | (lenemy_mask3 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 26) | (lenemy_mask3 >>> 7) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask2 << 42) | (lenemy_mask3 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 27) | (lenemy_mask3 >>> 6) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask2 << 43) | (lenemy_mask3 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 28) | (lenemy_mask3 >>> 5) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 7: case 16: case 25: case 34: case 43: case 52: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask0 << 42) | (lenemy_mask1 << 58) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25) | (lenemy_mask5 << 41)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 6) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask0 << 43) | (lenemy_mask1 << 59) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26) | (lenemy_mask5 << 42)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 5) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask0 << 44) | (lenemy_mask1 << 60) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27) | (lenemy_mask5 << 43)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 4) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask0 << 45) | (lenemy_mask1 << 61) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28) | (lenemy_mask5 << 44)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 3) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask0 << 46) | (lenemy_mask1 << 62) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29) | (lenemy_mask5 << 45)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 2) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask0 << 47) | (lenemy_mask1 << 63) | (lenemy_mask3 << 14) | (lenemy_mask4 << 30) | (lenemy_mask5 << 46)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1 >>> 1) | (lenemy_mask2 << 15) | (lenemy_mask3 << 31)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask0 << 48) | (lenemy_mask3 << 15) | (lenemy_mask4 << 31) | (lenemy_mask5 << 47)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask1) | (lenemy_mask2 << 16) | (lenemy_mask3 << 32)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask0 << 49) | (lenemy_mask3 << 16) | (lenemy_mask4 << 32) | (lenemy_mask5 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 15) | (lenemy_mask1 << 1) | (lenemy_mask2 << 17) | (lenemy_mask3 << 33)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask0 << 50) | (lenemy_mask3 << 17) | (lenemy_mask4 << 33) | (lenemy_mask5 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 14) | (lenemy_mask1 << 2) | (lenemy_mask2 << 18) | (lenemy_mask3 << 34)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask0 << 51) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34) | (lenemy_mask5 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 13) | (lenemy_mask1 << 3) | (lenemy_mask2 << 19) | (lenemy_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask0 << 52) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35) | (lenemy_mask5 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 12) | (lenemy_mask1 << 4) | (lenemy_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask0 << 53) | (lenemy_mask3 << 20) | (lenemy_mask4 << 36) | (lenemy_mask5 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 11) | (lenemy_mask1 << 5) | (lenemy_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask0 << 54) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21) | (lenemy_mask4 << 37) | (lenemy_mask5 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 10) | (lenemy_mask1 << 6) | (lenemy_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask0 << 55) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22) | (lenemy_mask4 << 38) | (lenemy_mask5 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 9) | (lenemy_mask1 << 7) | (lenemy_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask0 << 56) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23) | (lenemy_mask4 << 39) | (lenemy_mask5 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 8) | (lenemy_mask1 << 8) | (lenemy_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask0 << 57) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24) | (lenemy_mask4 << 40) | (lenemy_mask5 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 7) | (lenemy_mask1 << 9) | (lenemy_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask0 << 58) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25) | (lenemy_mask4 << 41) | (lenemy_mask5 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 6) | (lenemy_mask1 << 10) | (lenemy_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask0 << 59) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26) | (lenemy_mask4 << 42) | (lenemy_mask5 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 5) | (lenemy_mask1 << 11) | (lenemy_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask0 << 60) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27) | (lenemy_mask4 << 43) | (lenemy_mask5 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 4) | (lenemy_mask1 << 12) | (lenemy_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask0 << 61) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28) | (lenemy_mask4 << 44) | (lenemy_mask5 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 3) | (lenemy_mask1 << 13) | (lenemy_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask0 << 62) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29) | (lenemy_mask4 << 45) | (lenemy_mask5 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 2) | (lenemy_mask1 << 14) | (lenemy_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask0 << 63) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30) | (lenemy_mask4 << 46) | (lenemy_mask5 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 1) | (lenemy_mask1 << 15) | (lenemy_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask2 << 15) | (lenemy_mask3 << 31) | (lenemy_mask4 << 47) | (lenemy_mask5 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0) | (lenemy_mask1 << 16) | (lenemy_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask2 << 16) | (lenemy_mask3 << 32) | (lenemy_mask4 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 1) | (lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask5)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask2 << 17) | (lenemy_mask3 << 33) | (lenemy_mask4 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 2) | (lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask4 >>> 15) | (lenemy_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask2 << 18) | (lenemy_mask3 << 34) | (lenemy_mask4 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 3) | (lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask4 >>> 14) | (lenemy_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask2 << 19) | (lenemy_mask3 << 35) | (lenemy_mask4 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 4) | (lenemy_mask1 << 20) | (lenemy_mask4 >>> 13) | (lenemy_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask2 << 20) | (lenemy_mask3 << 36) | (lenemy_mask4 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask4 >>> 12) | (lenemy_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask1 << 5) | (lenemy_mask2 << 21) | (lenemy_mask3 << 37) | (lenemy_mask4 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask4 >>> 11) | (lenemy_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask1 << 6) | (lenemy_mask2 << 22) | (lenemy_mask3 << 38) | (lenemy_mask4 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask4 >>> 10) | (lenemy_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask1 << 7) | (lenemy_mask2 << 23) | (lenemy_mask3 << 39) | (lenemy_mask4 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask4 >>> 9) | (lenemy_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask1 << 8) | (lenemy_mask2 << 24) | (lenemy_mask3 << 40) | (lenemy_mask4 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask4 >>> 8) | (lenemy_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask1 << 9) | (lenemy_mask2 << 25) | (lenemy_mask3 << 41) | (lenemy_mask4 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask4 >>> 7) | (lenemy_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask1 << 10) | (lenemy_mask2 << 26) | (lenemy_mask3 << 42) | (lenemy_mask4 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask4 >>> 6) | (lenemy_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask1 << 11) | (lenemy_mask2 << 27) | (lenemy_mask3 << 43) | (lenemy_mask4 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask4 >>> 5) | (lenemy_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask1 << 12) | (lenemy_mask2 << 28) | (lenemy_mask3 << 44) | (lenemy_mask4 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask4 >>> 4) | (lenemy_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask1 << 13) | (lenemy_mask2 << 29) | (lenemy_mask3 << 45) | (lenemy_mask4 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask4 >>> 3) | (lenemy_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask1 << 14) | (lenemy_mask2 << 30) | (lenemy_mask3 << 46) | (lenemy_mask4 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask4 >>> 2) | (lenemy_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask1 << 15) | (lenemy_mask2 << 31) | (lenemy_mask3 << 47) | (lenemy_mask4 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask4 >>> 1) | (lenemy_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask1 << 16) | (lenemy_mask2 << 32) | (lenemy_mask3 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask4) | (lenemy_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask3 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask3 >>> 15) | (lenemy_mask4 << 1) | (lenemy_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask3 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask3 >>> 14) | (lenemy_mask4 << 2) | (lenemy_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask3 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 20) | (lenemy_mask3 >>> 13) | (lenemy_mask4 << 3) | (lenemy_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask1 << 20) | (lenemy_mask2 << 36) | (lenemy_mask3 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 21) | (lenemy_mask3 >>> 12) | (lenemy_mask4 << 4) | (lenemy_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask2 << 37) | (lenemy_mask3 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 22) | (lenemy_mask3 >>> 11) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask2 << 38) | (lenemy_mask3 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 23) | (lenemy_mask3 >>> 10) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask2 << 39) | (lenemy_mask3 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 24) | (lenemy_mask3 >>> 9) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask2 << 40) | (lenemy_mask3 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 25) | (lenemy_mask3 >>> 8) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask2 << 41) | (lenemy_mask3 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 26) | (lenemy_mask3 >>> 7) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask2 << 42) | (lenemy_mask3 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 27) | (lenemy_mask3 >>> 6) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask2 << 43) | (lenemy_mask3 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 28) | (lenemy_mask3 >>> 5) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask2 << 44) | (lenemy_mask3 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 29) | (lenemy_mask3 >>> 4) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask2 << 45) | (lenemy_mask3 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 30) | (lenemy_mask3 >>> 3) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask2 << 46) | (lenemy_mask3 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 31) | (lenemy_mask3 >>> 2) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask2 << 47) | (lenemy_mask3 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 32) | (lenemy_mask3 >>> 1) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask2 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 33) | (lenemy_mask3) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask2 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 34) | (lenemy_mask2 >>> 15) | (lenemy_mask3 << 1) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask2 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 35) | (lenemy_mask2 >>> 14) | (lenemy_mask3 << 2) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask2 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 13) | (lenemy_mask3 << 3) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask0 << 20) | (lenemy_mask1 << 36) | (lenemy_mask2 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 12) | (lenemy_mask3 << 4) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            case 8: case 17: case 26: case 35: case 44: case 53: 
                switch (myloc.x) {
                    case 0: 
                        enemy_mask[0] = ((lenemy_mask0 << 51) | (lenemy_mask3 << 18) | (lenemy_mask4 << 34) | (lenemy_mask5 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 13) | (lenemy_mask1 << 3) | (lenemy_mask2 << 19) | (lenemy_mask3 << 35)) & 0xfffffffffL;
                        break outer;
                    case 1: 
                        enemy_mask[0] = ((lenemy_mask0 << 52) | (lenemy_mask3 << 19) | (lenemy_mask4 << 35) | (lenemy_mask5 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 12) | (lenemy_mask1 << 4) | (lenemy_mask2 << 20)) & 0xfffffffffL;
                        break outer;
                    case 2: 
                        enemy_mask[0] = ((lenemy_mask0 << 53) | (lenemy_mask3 << 20) | (lenemy_mask4 << 36) | (lenemy_mask5 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 11) | (lenemy_mask1 << 5) | (lenemy_mask2 << 21)) & 0xfffffffffL;
                        break outer;
                    case 3: 
                        enemy_mask[0] = ((lenemy_mask0 << 54) | (lenemy_mask2 << 5) | (lenemy_mask3 << 21) | (lenemy_mask4 << 37) | (lenemy_mask5 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 10) | (lenemy_mask1 << 6) | (lenemy_mask2 << 22)) & 0xfffffffffL;
                        break outer;
                    case 4: 
                        enemy_mask[0] = ((lenemy_mask0 << 55) | (lenemy_mask2 << 6) | (lenemy_mask3 << 22) | (lenemy_mask4 << 38) | (lenemy_mask5 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 9) | (lenemy_mask1 << 7) | (lenemy_mask2 << 23)) & 0xfffffffffL;
                        break outer;
                    case 5: 
                        enemy_mask[0] = ((lenemy_mask0 << 56) | (lenemy_mask2 << 7) | (lenemy_mask3 << 23) | (lenemy_mask4 << 39) | (lenemy_mask5 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 8) | (lenemy_mask1 << 8) | (lenemy_mask2 << 24)) & 0xfffffffffL;
                        break outer;
                    case 6: 
                        enemy_mask[0] = ((lenemy_mask0 << 57) | (lenemy_mask2 << 8) | (lenemy_mask3 << 24) | (lenemy_mask4 << 40) | (lenemy_mask5 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 7) | (lenemy_mask1 << 9) | (lenemy_mask2 << 25)) & 0xfffffffffL;
                        break outer;
                    case 7: 
                        enemy_mask[0] = ((lenemy_mask0 << 58) | (lenemy_mask2 << 9) | (lenemy_mask3 << 25) | (lenemy_mask4 << 41) | (lenemy_mask5 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 6) | (lenemy_mask1 << 10) | (lenemy_mask2 << 26)) & 0xfffffffffL;
                        break outer;
                    case 8: 
                        enemy_mask[0] = ((lenemy_mask0 << 59) | (lenemy_mask2 << 10) | (lenemy_mask3 << 26) | (lenemy_mask4 << 42) | (lenemy_mask5 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 5) | (lenemy_mask1 << 11) | (lenemy_mask2 << 27)) & 0xfffffffffL;
                        break outer;
                    case 9: 
                        enemy_mask[0] = ((lenemy_mask0 << 60) | (lenemy_mask2 << 11) | (lenemy_mask3 << 27) | (lenemy_mask4 << 43) | (lenemy_mask5 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 4) | (lenemy_mask1 << 12) | (lenemy_mask2 << 28)) & 0xfffffffffL;
                        break outer;
                    case 10: 
                        enemy_mask[0] = ((lenemy_mask0 << 61) | (lenemy_mask2 << 12) | (lenemy_mask3 << 28) | (lenemy_mask4 << 44) | (lenemy_mask5 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 3) | (lenemy_mask1 << 13) | (lenemy_mask2 << 29)) & 0xfffffffffL;
                        break outer;
                    case 11: 
                        enemy_mask[0] = ((lenemy_mask0 << 62) | (lenemy_mask2 << 13) | (lenemy_mask3 << 29) | (lenemy_mask4 << 45) | (lenemy_mask5 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 2) | (lenemy_mask1 << 14) | (lenemy_mask2 << 30)) & 0xfffffffffL;
                        break outer;
                    case 12: 
                        enemy_mask[0] = ((lenemy_mask0 << 63) | (lenemy_mask2 << 14) | (lenemy_mask3 << 30) | (lenemy_mask4 << 46) | (lenemy_mask5 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 >>> 1) | (lenemy_mask1 << 15) | (lenemy_mask2 << 31)) & 0xfffffffffL;
                        break outer;
                    case 13: 
                        enemy_mask[0] = ((lenemy_mask2 << 15) | (lenemy_mask3 << 31) | (lenemy_mask4 << 47) | (lenemy_mask5 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0) | (lenemy_mask1 << 16) | (lenemy_mask2 << 32)) & 0xfffffffffL;
                        break outer;
                    case 14: 
                        enemy_mask[0] = ((lenemy_mask2 << 16) | (lenemy_mask3 << 32) | (lenemy_mask4 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 1) | (lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask5)) & 0xfffffffffL;
                        break outer;
                    case 15: 
                        enemy_mask[0] = ((lenemy_mask2 << 17) | (lenemy_mask3 << 33) | (lenemy_mask4 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 2) | (lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask4 >>> 15) | (lenemy_mask5 << 1)) & 0xfffffffffL;
                        break outer;
                    case 16: 
                        enemy_mask[0] = ((lenemy_mask2 << 18) | (lenemy_mask3 << 34) | (lenemy_mask4 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 3) | (lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask4 >>> 14) | (lenemy_mask5 << 2)) & 0xfffffffffL;
                        break outer;
                    case 17: 
                        enemy_mask[0] = ((lenemy_mask2 << 19) | (lenemy_mask3 << 35) | (lenemy_mask4 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 4) | (lenemy_mask1 << 20) | (lenemy_mask4 >>> 13) | (lenemy_mask5 << 3)) & 0xfffffffffL;
                        break outer;
                    case 18: 
                        enemy_mask[0] = ((lenemy_mask2 << 20) | (lenemy_mask3 << 36) | (lenemy_mask4 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask4 >>> 12) | (lenemy_mask5 << 4)) & 0xfffffffffL;
                        break outer;
                    case 19: 
                        enemy_mask[0] = ((lenemy_mask1 << 5) | (lenemy_mask2 << 21) | (lenemy_mask3 << 37) | (lenemy_mask4 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask4 >>> 11) | (lenemy_mask5 << 5)) & 0xfffffffffL;
                        break outer;
                    case 20: 
                        enemy_mask[0] = ((lenemy_mask1 << 6) | (lenemy_mask2 << 22) | (lenemy_mask3 << 38) | (lenemy_mask4 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask4 >>> 10) | (lenemy_mask5 << 6)) & 0xfffffffffL;
                        break outer;
                    case 21: 
                        enemy_mask[0] = ((lenemy_mask1 << 7) | (lenemy_mask2 << 23) | (lenemy_mask3 << 39) | (lenemy_mask4 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask4 >>> 9) | (lenemy_mask5 << 7)) & 0xfffffffffL;
                        break outer;
                    case 22: 
                        enemy_mask[0] = ((lenemy_mask1 << 8) | (lenemy_mask2 << 24) | (lenemy_mask3 << 40) | (lenemy_mask4 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask4 >>> 8) | (lenemy_mask5 << 8)) & 0xfffffffffL;
                        break outer;
                    case 23: 
                        enemy_mask[0] = ((lenemy_mask1 << 9) | (lenemy_mask2 << 25) | (lenemy_mask3 << 41) | (lenemy_mask4 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask4 >>> 7) | (lenemy_mask5 << 9)) & 0xfffffffffL;
                        break outer;
                    case 24: 
                        enemy_mask[0] = ((lenemy_mask1 << 10) | (lenemy_mask2 << 26) | (lenemy_mask3 << 42) | (lenemy_mask4 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask4 >>> 6) | (lenemy_mask5 << 10)) & 0xfffffffffL;
                        break outer;
                    case 25: 
                        enemy_mask[0] = ((lenemy_mask1 << 11) | (lenemy_mask2 << 27) | (lenemy_mask3 << 43) | (lenemy_mask4 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask4 >>> 5) | (lenemy_mask5 << 11)) & 0xfffffffffL;
                        break outer;
                    case 26: 
                        enemy_mask[0] = ((lenemy_mask1 << 12) | (lenemy_mask2 << 28) | (lenemy_mask3 << 44) | (lenemy_mask4 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask4 >>> 4) | (lenemy_mask5 << 12)) & 0xfffffffffL;
                        break outer;
                    case 27: 
                        enemy_mask[0] = ((lenemy_mask1 << 13) | (lenemy_mask2 << 29) | (lenemy_mask3 << 45) | (lenemy_mask4 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask4 >>> 3) | (lenemy_mask5 << 13)) & 0xfffffffffL;
                        break outer;
                    case 28: 
                        enemy_mask[0] = ((lenemy_mask1 << 14) | (lenemy_mask2 << 30) | (lenemy_mask3 << 46) | (lenemy_mask4 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask4 >>> 2) | (lenemy_mask5 << 14)) & 0xfffffffffL;
                        break outer;
                    case 29: 
                        enemy_mask[0] = ((lenemy_mask1 << 15) | (lenemy_mask2 << 31) | (lenemy_mask3 << 47) | (lenemy_mask4 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask4 >>> 1) | (lenemy_mask5 << 15)) & 0xfffffffffL;
                        break outer;
                    case 30: 
                        enemy_mask[0] = ((lenemy_mask1 << 16) | (lenemy_mask2 << 32) | (lenemy_mask3 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask4) | (lenemy_mask5 << 16)) & 0xfffffffffL;
                        break outer;
                    case 31: 
                        enemy_mask[0] = ((lenemy_mask1 << 17) | (lenemy_mask2 << 33) | (lenemy_mask3 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask3 >>> 15) | (lenemy_mask4 << 1) | (lenemy_mask5 << 17)) & 0xfffffffffL;
                        break outer;
                    case 32: 
                        enemy_mask[0] = ((lenemy_mask1 << 18) | (lenemy_mask2 << 34) | (lenemy_mask3 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask3 >>> 14) | (lenemy_mask4 << 2) | (lenemy_mask5 << 18)) & 0xfffffffffL;
                        break outer;
                    case 33: 
                        enemy_mask[0] = ((lenemy_mask1 << 19) | (lenemy_mask2 << 35) | (lenemy_mask3 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 20) | (lenemy_mask3 >>> 13) | (lenemy_mask4 << 3) | (lenemy_mask5 << 19)) & 0xfffffffffL;
                        break outer;
                    case 34: 
                        enemy_mask[0] = ((lenemy_mask1 << 20) | (lenemy_mask2 << 36) | (lenemy_mask3 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 21) | (lenemy_mask3 >>> 12) | (lenemy_mask4 << 4) | (lenemy_mask5 << 20)) & 0xfffffffffL;
                        break outer;
                    case 35: 
                        enemy_mask[0] = ((lenemy_mask0 << 5) | (lenemy_mask1 << 21) | (lenemy_mask2 << 37) | (lenemy_mask3 << 53)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 22) | (lenemy_mask3 >>> 11) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffL;
                        break outer;
                    case 36: 
                        enemy_mask[0] = ((lenemy_mask0 << 6) | (lenemy_mask1 << 22) | (lenemy_mask2 << 38) | (lenemy_mask3 << 54)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 23) | (lenemy_mask3 >>> 10) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffL;
                        break outer;
                    case 37: 
                        enemy_mask[0] = ((lenemy_mask0 << 7) | (lenemy_mask1 << 23) | (lenemy_mask2 << 39) | (lenemy_mask3 << 55)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 24) | (lenemy_mask3 >>> 9) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffL;
                        break outer;
                    case 38: 
                        enemy_mask[0] = ((lenemy_mask0 << 8) | (lenemy_mask1 << 24) | (lenemy_mask2 << 40) | (lenemy_mask3 << 56)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 25) | (lenemy_mask3 >>> 8) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffL;
                        break outer;
                    case 39: 
                        enemy_mask[0] = ((lenemy_mask0 << 9) | (lenemy_mask1 << 25) | (lenemy_mask2 << 41) | (lenemy_mask3 << 57)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 26) | (lenemy_mask3 >>> 7) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffL;
                        break outer;
                    case 40: 
                        enemy_mask[0] = ((lenemy_mask0 << 10) | (lenemy_mask1 << 26) | (lenemy_mask2 << 42) | (lenemy_mask3 << 58)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 27) | (lenemy_mask3 >>> 6) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffL;
                        break outer;
                    case 41: 
                        enemy_mask[0] = ((lenemy_mask0 << 11) | (lenemy_mask1 << 27) | (lenemy_mask2 << 43) | (lenemy_mask3 << 59)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 28) | (lenemy_mask3 >>> 5) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffL;
                        break outer;
                    case 42: 
                        enemy_mask[0] = ((lenemy_mask0 << 12) | (lenemy_mask1 << 28) | (lenemy_mask2 << 44) | (lenemy_mask3 << 60)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 29) | (lenemy_mask3 >>> 4) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffL;
                        break outer;
                    case 43: 
                        enemy_mask[0] = ((lenemy_mask0 << 13) | (lenemy_mask1 << 29) | (lenemy_mask2 << 45) | (lenemy_mask3 << 61)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 30) | (lenemy_mask3 >>> 3) | (lenemy_mask4 << 13) | (lenemy_mask5 << 29)) & 0xfffffffffL;
                        break outer;
                    case 44: 
                        enemy_mask[0] = ((lenemy_mask0 << 14) | (lenemy_mask1 << 30) | (lenemy_mask2 << 46) | (lenemy_mask3 << 62)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 31) | (lenemy_mask3 >>> 2) | (lenemy_mask4 << 14) | (lenemy_mask5 << 30)) & 0xfffffffffL;
                        break outer;
                    case 45: 
                        enemy_mask[0] = ((lenemy_mask0 << 15) | (lenemy_mask1 << 31) | (lenemy_mask2 << 47) | (lenemy_mask3 << 63)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 32) | (lenemy_mask3 >>> 1) | (lenemy_mask4 << 15) | (lenemy_mask5 << 31)) & 0xfffffffffL;
                        break outer;
                    case 46: 
                        enemy_mask[0] = ((lenemy_mask0 << 16) | (lenemy_mask1 << 32) | (lenemy_mask2 << 48)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 33) | (lenemy_mask3) | (lenemy_mask4 << 16) | (lenemy_mask5 << 32)) & 0xfffffffffL;
                        break outer;
                    case 47: 
                        enemy_mask[0] = ((lenemy_mask0 << 17) | (lenemy_mask1 << 33) | (lenemy_mask2 << 49)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 34) | (lenemy_mask2 >>> 15) | (lenemy_mask3 << 1) | (lenemy_mask4 << 17) | (lenemy_mask5 << 33)) & 0xfffffffffL;
                        break outer;
                    case 48: 
                        enemy_mask[0] = ((lenemy_mask0 << 18) | (lenemy_mask1 << 34) | (lenemy_mask2 << 50)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask0 << 35) | (lenemy_mask2 >>> 14) | (lenemy_mask3 << 2) | (lenemy_mask4 << 18) | (lenemy_mask5 << 34)) & 0xfffffffffL;
                        break outer;
                    case 49: 
                        enemy_mask[0] = ((lenemy_mask0 << 19) | (lenemy_mask1 << 35) | (lenemy_mask2 << 51)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 13) | (lenemy_mask3 << 3) | (lenemy_mask4 << 19) | (lenemy_mask5 << 35)) & 0xfffffffffL;
                        break outer;
                    case 50: 
                        enemy_mask[0] = ((lenemy_mask0 << 20) | (lenemy_mask1 << 36) | (lenemy_mask2 << 52)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 12) | (lenemy_mask3 << 4) | (lenemy_mask4 << 20) | (lenemy_mask5 << 36)) & 0xfffffffffL;
                        break outer;
                    case 51: 
                        enemy_mask[0] = ((lenemy_mask0 << 21) | (lenemy_mask1 << 37) | (lenemy_mask2 << 53) | (lenemy_mask5 << 20)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 11) | (lenemy_mask3 << 5) | (lenemy_mask4 << 21)) & 0xfffffffffL;
                        break outer;
                    case 52: 
                        enemy_mask[0] = ((lenemy_mask0 << 22) | (lenemy_mask1 << 38) | (lenemy_mask2 << 54) | (lenemy_mask4 << 5) | (lenemy_mask5 << 21)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 10) | (lenemy_mask3 << 6) | (lenemy_mask4 << 22)) & 0xfffffffffL;
                        break outer;
                    case 53: 
                        enemy_mask[0] = ((lenemy_mask0 << 23) | (lenemy_mask1 << 39) | (lenemy_mask2 << 55) | (lenemy_mask4 << 6) | (lenemy_mask5 << 22)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 9) | (lenemy_mask3 << 7) | (lenemy_mask4 << 23)) & 0xfffffffffL;
                        break outer;
                    case 54: 
                        enemy_mask[0] = ((lenemy_mask0 << 24) | (lenemy_mask1 << 40) | (lenemy_mask2 << 56) | (lenemy_mask4 << 7) | (lenemy_mask5 << 23)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 8) | (lenemy_mask3 << 8) | (lenemy_mask4 << 24)) & 0xfffffffffL;
                        break outer;
                    case 55: 
                        enemy_mask[0] = ((lenemy_mask0 << 25) | (lenemy_mask1 << 41) | (lenemy_mask2 << 57) | (lenemy_mask4 << 8) | (lenemy_mask5 << 24)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 7) | (lenemy_mask3 << 9) | (lenemy_mask4 << 25)) & 0xfffffffffL;
                        break outer;
                    case 56: 
                        enemy_mask[0] = ((lenemy_mask0 << 26) | (lenemy_mask1 << 42) | (lenemy_mask2 << 58) | (lenemy_mask4 << 9) | (lenemy_mask5 << 25)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 6) | (lenemy_mask3 << 10) | (lenemy_mask4 << 26)) & 0xfffffffffL;
                        break outer;
                    case 57: 
                        enemy_mask[0] = ((lenemy_mask0 << 27) | (lenemy_mask1 << 43) | (lenemy_mask2 << 59) | (lenemy_mask4 << 10) | (lenemy_mask5 << 26)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 5) | (lenemy_mask3 << 11) | (lenemy_mask4 << 27)) & 0xfffffffffL;
                        break outer;
                    case 58: 
                        enemy_mask[0] = ((lenemy_mask0 << 28) | (lenemy_mask1 << 44) | (lenemy_mask2 << 60) | (lenemy_mask4 << 11) | (lenemy_mask5 << 27)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 4) | (lenemy_mask3 << 12) | (lenemy_mask4 << 28)) & 0xfffffffffL;
                        break outer;
                    case 59: 
                        enemy_mask[0] = ((lenemy_mask0 << 29) | (lenemy_mask1 << 45) | (lenemy_mask2 << 61) | (lenemy_mask4 << 12) | (lenemy_mask5 << 28)) & 0xfffffffffff80000L;
                        enemy_mask[1] = ((lenemy_mask2 >>> 3) | (lenemy_mask3 << 13) | (lenemy_mask4 << 29)) & 0xfffffffffL;
                        break outer;
                    default: break outer;
                }
            default: 
        }
        hasLaunchersNear = ((enemy_mask[0] | enemy_mask[1]) > 0);
        hasCarriersNear = (cc > 0);
    }

    /*
     * Return the direction which minimizes the distance to attacked soldiers.
     * Ignores walls and cooldown for now.
     * We also ignore wraparound issues, but this may be ok, since we don't account for walls.
     * I.e the wraparound squares will never be closer then the squares that made them.
     * 
     * Also this isn't particularly optimized. The most obvious improvement - 
     * Change all statics to locals to avoid the extra loads.
     * Change the arrays to seperate variables so we don't have to load the reference every time.
     */

    Direction advance() throws GameActionException {
        long mask0 = friend_mask[0];
        long mask1 = friend_mask[1];
        int fe_mask_width = FE_MASK_WIDTH;
        int fe_mask_height = FE_MASK_HEIGHT;
        int shift = (fe_mask_height - 1) * (fe_mask_width);
        for (int i = 5; i-- > 0;) {
            mask0 |= (mask0 << 1) | (mask0 >> 1) | (mask0 << fe_mask_width) | (mask0 >> fe_mask_width) | (mask1 << shift);
            mask1 |= (mask1 << 1) | (mask1 >> 1) | (mask1 << fe_mask_width) | (mask0 >> shift) | (mask1 >> fe_mask_width);
            switch ((int)(mask1 & 0b0000111000000001110000)) {
                case (0b0000000000000000000000): 
                    break;
                case (0b0000000000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    break;
                case (0b0000000000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    break;
                case (0b0000000000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    break;
                case (0b0000000000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    break;
                case (0b0000000000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    break;
                case (0b0000000000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    break;
                case (0b0000000000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    break;
                case (0b0000001000000000000000): 
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000010000000000000000): 
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000000000000): 
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000100000000000000000): 
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000000000000): 
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000000000000): 
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000000000000): 
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                default: 
            }
            switch ((int)(mask0 >> (shift) & 0b00001110000)) {
                case (0b00000000000):
                    break;
                case (0b00000010000):
                    if (rc.canMove(Direction.SOUTHWEST)) return Direction.SOUTHWEST;
                    break;
                case (0b00000100000):
                    if (rc.canMove(Direction.SOUTH)) return Direction.SOUTH;
                    break;
                case (0b00000110000):
                    if (rc.canMove(Direction.SOUTHWEST)) return Direction.SOUTHWEST;
                    if (rc.canMove(Direction.SOUTH)) return Direction.SOUTH;
                    break;
                case (0b00001000000):
                    if (rc.canMove(Direction.SOUTHEAST)) return Direction.SOUTHEAST;
                    break;
                case (0b00001010000):
                    if (rc.canMove(Direction.SOUTHWEST)) return Direction.SOUTHWEST;
                    if (rc.canMove(Direction.SOUTHEAST)) return Direction.SOUTHEAST;
                    break;
                case (0b00001100000):
                    if (rc.canMove(Direction.SOUTH)) return Direction.SOUTH;
                    if (rc.canMove(Direction.SOUTHEAST)) return Direction.SOUTHEAST;
                    break;
                case (0b00001110000):
                    if (rc.canMove(Direction.SOUTHWEST)) return Direction.SOUTHWEST;
                    if (rc.canMove(Direction.SOUTH)) return Direction.SOUTH;
                    if (rc.canMove(Direction.SOUTHEAST)) return Direction.SOUTHEAST;
                    break;
                default: 
            }
        }
        return null;
    }

    void displayMap() throws GameActionException {
        long[] mask = friend_mask;
        MapLocation myloc = rc.getLocation();
        for (int i = 0; i < FE_MASK_HEIGHT; i++) {
            for (int j = 0; j < FE_MASK_WIDTH; j++) {
                if (Clock.getBytecodesLeft() < 1000) break;
                int on = (int) ((mask[0] >> (i * FE_MASK_WIDTH + j)) & 1);
                int x = j + (myloc.x - FE_MASK_WIDTH / 2);
                int y = i + (myloc.y - FE_MASK_HEIGHT);
                MapLocation spot = new MapLocation(x, y);
                rc.setIndicatorDot(spot, on * 255, 0, 0);
            }
        }
        for (int i = 0; i < FE_MASK_HEIGHT; i++) {
            for (int j = 0; j < FE_MASK_WIDTH; j++) {
                if (Clock.getBytecodesLeft() < 1000) break;
                int on = (int) ((mask[1] >> (i * FE_MASK_WIDTH + j)) & 1);
                int x = j + (myloc.x - FE_MASK_WIDTH / 2);
                int y = i + (myloc.y);
                MapLocation spot = new MapLocation(x, y);
                rc.setIndicatorDot(spot, on * 255, 0, 0);
            }
        }
        rc.setIndicatorString("Map: " + Long.toBinaryString(mask[0]));
    }
}
