d = dict()

chars = "aa"

# def func(chars):
# #     for i in chars:
# #         if i in d:
# #             d[i] += 1
# #         else:
# #             d[i] = 1
# #
# #     for ite in range(len(chars)):
# #         if d[chars[ite]] == 1:
# #             return ite+1
# #     return -1
# # print(func(chars))
# # for chars in inputs:
#     ans = len(chars)
#     for i in range(1, len(chars)):
#         pre = list(chars[:i])
#         suf = list(chars)
#         cnt = 0
#         l = min(len(pre), len(suf))
#
#         for j in range(l):
#             if pre[j] != suf[j]:
#                 break
#             cnt += 1
#         print(pre, suf, l, cnt)
#         ans += cnt
#     # output.append(ans)
# # return output
# print(func(chars))


def getZarr(s, n, Z):
    L, R, k = 0, 0, 0
    for i in range(n):
        if i > R:
            L, R = i, i
            while R < n and s[R - L] == s[R]:
                R += 1
            Z[i] = R - L
            R -= 1
        else:
            k = i - L
            if Z[k] < R - i + 1:
                Z[i] = Z[k]
            else:
                L = i
                while R < n and s[R - L] == s[R]:
                    R += 1
                Z[i] = R - L
                R -= 1


def sumSimilarities(s, n):
    Z = [0 for i in range(n)]
    getZarr(s, n, Z)

    total = n
    for i in range(n):
        total += Z[i]
    return total
inputs = ["ababaa", "aa"]


output = []
for s in inputs:
    n = len(s)
    ans = sumSimilarities(s, n)
    output.append(ans)
print(output)

