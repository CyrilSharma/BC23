FE_MASK_WIDTH = 11
FE_MASK_HEIGHT = 5

directions = [
    "Direction.NORTH",
    "Direction.NORTHEAST",
    "Direction.EAST",
    "Direction.SOUTHEAST",
    "Direction.SOUTH",
    "Direction.SOUTHWEST",
    "Direction.WEST",
    "Direction.NORTHWEST",
    "Direction.CENTER"
]

def map6(num):
    mask = 0
    shift = (FE_MASK_WIDTH // 2) - 1
    mask |= (num & 0b111) << shift
    mask |= ((num >> 3) & 0b111) << (FE_MASK_WIDTH + shift)
    return mask

def dir6(num):
    dirs = []
    if (num & 0b1):        dirs.append("Direction.WEST")
    if ((num >> 1) & 0b1): dirs.append("Direction.CENTER")
    if ((num >> 2) & 0b1): dirs.append("Direction.EAST")
    if ((num >> 3) & 0b1): dirs.append("Direction.NORTHWEST")
    if ((num >> 4) & 0b1): dirs.append("Direction.NORTH")
    if ((num >> 5) & 0b1): dirs.append("Direction.NORTHEAST")
    return dirs

# We need to shift this to be in an integer range anyways.
def map3(num):
    mask = 0
    mask |= ((num & 0b111) << ((FE_MASK_WIDTH // 2) - 1))
    return mask

def dir3(num):
    dirs = []
    if (num & 0b1):        dirs.append("Direction.SOUTHWEST")
    if ((num >> 1) & 0b1): dirs.append("Direction.SOUTH")
    if ((num >> 2) & 0b1): dirs.append("Direction.SOUTHEAST")
    return dirs

def binary_str(num, bit_width):
    binary_representation = format(num, f'0{bit_width}b')
    return f'0b{binary_representation}'

indent = ' ' * 4
print("switch () {")
for i in range(1 << 6):
    print(f"{indent}case ({binary_str(map6(i), 2 * FE_MASK_WIDTH)}): ")
    # This is a kludge. Ideally we just generate the wall-mask
    # Then we'll never have to check if movement is possible.
    for dir in dir6(i):
        print(f"{indent * 2}if (rc.canMove({dir})) return {dir};");
    print(f"{indent * 2}break;")
print(f"{indent}default: ")
print("}")

print("switch () {")
for i in range(1 << 3):
    print(f"{indent}case ({binary_str(map3(i), FE_MASK_WIDTH)}):")
    for dir in dir3(i):
        print(f"{indent * 2}if (rc.canMove({dir})) return {dir};");
    print(f"{indent * 2}break;")
print(f"{indent}default: ")
print("}")
