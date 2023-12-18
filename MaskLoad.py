FE_MASK_WIDTH = 11
FE_MASK_HEIGHT = 5

indent = ' ' * 4
print("switch (r.location.y - bly) {{")
for y in range(FE_MASK_HEIGHT * 2):
    print(f"{indent}case ({y}):")
    print(f"{indent * 2}switch (r.location.x - blx) {{")
    for x in range(FE_MASK_WIDTH):
        print(f"{indent * 3}case ({x}):")
        if (y < FE_MASK_HEIGHT):
            bit = (1 << (y * FE_MASK_WIDTH + x));
            print(f"{indent * 4}tfriend_mask0 |= {bit}L;")
            print(f"{indent * 4}if (r.health + 10 < phealth) {{");
            print(f"{indent * 5}att_friend_mask0 |= {bit}L;");
            print(f"{indent * 4}}}");
        else:
            bit = (1 << ((y - FE_MASK_HEIGHT) * (FE_MASK_WIDTH) + x));
            print(f"{indent * 4}tfriend_mask1 |= {bit}L;")
            print(f"{indent * 4}if (r.health + 10 < phealth) {{");
            print(f"{indent * 5}att_friend_mask1 |= {bit}L;");
            print(f"{indent * 4}}}");
        print(f"{indent * 4}break;")
    print(f"{indent * 3}default: ")
    print(f"{indent * 2}}}")
print(f"{indent}default: ")
print("}")


print("switch (r.location.y - bly) {{")
for y in range(FE_MASK_HEIGHT * 2):
    print(f"{indent}case ({y}):")
    print(f"{indent * 2}switch (r.location.x - blx) {{")
    for x in range(FE_MASK_WIDTH):
        if (y < FE_MASK_HEIGHT):
            bit = (1 << (y * FE_MASK_WIDTH + x));
            print(f"{indent * 3}case ({x}): tenemy_mask0 |= {bit}L; break;")
        else:
            bit = (1 << ((y - FE_MASK_HEIGHT) * (FE_MASK_WIDTH) + x));
            print(f"{indent * 3}case ({x}): tenemy_mask1 |= {bit}L; break;")
    print(f"{indent * 3}default: ")
    print(f"{indent * 2}}}")
print(f"{indent}default: ")
print("}")
