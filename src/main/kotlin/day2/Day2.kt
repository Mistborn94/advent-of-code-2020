package day2

fun solveA(lines: List<String>): Int = lines.count { passwordPolicyAMatches(it) }

fun passwordPolicyAMatches(it: String): Boolean {
    val (policyMin, policyMax, letter, _, password) = it.split(' ', ':', '-').map { it.trim() }
    val letterCount = password.count { it == letter[0] }

    return letterCount >= policyMin.toInt() && letterCount <= policyMax.toInt();
}

fun solveB(lines: List<String>): Int = lines.count { passwordPolicyBMatches(it) }

fun passwordPolicyBMatches(it: String): Boolean {
    val (policyFirst, policyLast, letter, _, password) = it.split(' ', ':', '-').map { it.trim() }

    return (password[policyFirst.toInt() - 1] == letter[0]) xor (password[policyLast.toInt() - 1] == letter[0])
}
