FE_MASK_WIDTH = 11
FE_MASK_HEIGHT = 5

def map8(num):
    y = num >> 3
    x = num & 0b111;
    shift = (FE_MASK_WIDTH // 2) - 1
    mask |= (num & 0b111) << shift
    mask |= ((num >> 3) & 0b111) << (FE_MASK_WIDTH + shift)
    return mask

def binary_str(num, bit_width):
    binary_representation = format(num, f'0{bit_width}b')
    return f'0b{binary_representation}'

indent = ' ' * 4

print("switch ((r.location.y - bly) << 4 + (r.location.x - blx)) {{")
for i in range(1 << 8):
    y = i >> 4; x = i & 0b1111;
    if (x >= FE_MASK_WIDTH): continue;
    if (y >= FE_MASK_HEIGHT * 2): continue;
    print(f"{indent}case ({i}):")
    if (y < FE_MASK_HEIGHT):
        bit = (1 << (y * FE_MASK_WIDTH + x));
        print(f"{indent * 2}tfriend_mask0 |= {bit}L;")
        print(f"{indent * 2}if (r.health + 10 < phealth) {{");
        print(f"{indent * 3}att_friend_mask0 |= {bit}L;");
        print(f"{indent * 2}}}");
    else:
        bit = (1 << ((y - FE_MASK_HEIGHT) * (FE_MASK_WIDTH) + x));
        print(f"{indent * 2}tfriend_mask1 |= {bit}L;")
        print(f"{indent * 2}if (r.health + 10 < phealth) {{");
        print(f"{indent * 3}att_friend_mask1 |= {bit}L;");
        print(f"{indent * 2}}}");
    print(f"{indent * 2}break;")
print(f"{indent}default: ")
print("}")

print("switch ((r.location.y - bly) << 4 + (r.location.x - blx)) {{")
for i in range(1 << 8):
    y = i >> 4; x = i & 0b1111;
    if (x >= FE_MASK_WIDTH): continue;
    if (y >= FE_MASK_HEIGHT * 2): continue;
    if (y < FE_MASK_HEIGHT):
        bit = (1 << (y * FE_MASK_WIDTH + x));
        print(f"{indent}case ({i}): tenemy_mask0 |= {bit}L; break;")
    else:
        bit = (1 << ((y - FE_MASK_HEIGHT) * (FE_MASK_WIDTH) + x));
        print(f"{indent}case ({i}): tenemy_mask1 |= {bit}L; break;")
print(f"{indent}default: ")
print("}")
