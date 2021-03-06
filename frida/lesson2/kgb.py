from z3 import *

p = [ord(x) for x in "V@]EAASB\u0012WZF\u0012e,a$7(&am2(3.\u0003"]
r = [ord(x)
     for x in "\u0000dslp}oQ\u0000 dks$|M\u0000h +AYQg\u0000P*!M$gQ\u0000"]

res_p = [0] * len(p)
for i in range(int(len(p) / 2)):
    c = p[i]
    res_p[i] = p[len(p) - i - 1] ^ ord('A')
    res_p[len(p) - i - 1] = c ^ ord('2')

print(''.join([chr(x) for x in res_p]))

res_r = [0] * len(r)

s = Solver()
x = [BitVec('x[%d]' % i, 32) for i in range(len(res_r))]
l = len(r)
for i in range(len(res_r)):
    s.add((x[i] >> (i % 8)) ^ x[i] == r[l - i - 1])

result = []

if s.check() == sat:
    model = s.model()
    for i in range(len(r)):
        c = model[x[i]]
        if c != None:
            result.append(c.as_long().real)

print(''.join([chr(x) for x in result]))
