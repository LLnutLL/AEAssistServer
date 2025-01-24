package xx.xxxx.controller.entity

/**
 * @author Hello
 * create on 2025.01.24 19:22
 */

data class AEResponse(
    val errorCode: Int = 0,
    val msg: String = "",
    val sigs: Map<String, String> = mapOf(),
    val level: Int = 4,
    val expireTime: Long = 123456,
    val firstLoginTime: Long = 0,
    val inviteRecords: List<String> = listOf(),
    )