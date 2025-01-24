package xx.xxxx.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xx.xxxx.Main
import xx.xxxx.controller.entity.AEResponse
import xx.xxxx.utils.FileUtils
import java.io.File
import java.io.FileInputStream

/**
 * @author Hello
 * create on 2025.01.24 19:17
 */

@Controller
class AEServerController(
    private val jackson: ObjectMapper,
) {
    private val log = KotlinLogging.logger { }
    private final val path = "${FileUtils.getCurrentPath(Main::class.java)}/ACR"
    private final val acrPath = "$path/listACR.json"
    init {
        File(path).mkdirs()
        if (!File(acrPath).exists()) {
            File(acrPath).createNewFile()
        }
    }

    @RequestMapping("/**")
    @ResponseBody
    fun filter(
        request: HttpServletRequest?,
        @RequestHeader header: Map<String?, String?>?,
        @RequestBody(required = false) body: Map<String?, String?>?,
    ): String {
        return "NULL"
    }


    @GetMapping("/ListACR")
    @ResponseBody
    fun listACR(): String {
        FileInputStream("$path/listACR.json").use { fileInputStream ->
            return fileInputStream.readBytes().toString(Charsets.UTF_8)
        }
    }


    @RequestMapping("/DownloadACR")
    fun download(author: String?, @RequestHeader header: Map<String?, String?>?, response: HttpServletResponse) {

        response.contentType = "application/octet-stream"
        try {
            FileInputStream("$path/$author").use { fileInputStream ->
                response.outputStream.use { outputStream ->
                    val buffer = ByteArray(1024)
                    var len: Int
                    while ((fileInputStream.read(buffer).also { len = it }) > 0) {
                        outputStream.write(buffer, 0, len)
                    }
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @GetMapping(value = ["/"])
    @ResponseBody
    fun system(): String {
        return "你所连接的服务器非AE正版服务器\n" +
                " 因此你的账户信息没有交由AE管理\n" +
                " 但是如果你连接的是非受信任的服务器仍然不受保障\n" +
                "请远离闲鱼该AE服务项目已开源 https://github.com/LLnutLL/AEAssistServer"
    }


    @GetMapping(value = ["/ListTimelineByTerrId"])
    @ResponseBody
    fun listTimeLine(level: String?, terrId: String?, jobId: String?, clear: String?, startIndex: String?): List<Any> {
        return ArrayList()
    }

    @PostMapping(value = ["/Verify"])
    @ResponseBody
    fun verify(@RequestBody body: Map<String?, String?>): String {
        val name: String? = body["playerName"]
        val key: String? = body["key"]
        val mac: String? = body["macId"]
        val serverName: String? = body["serverName"]
        val accountId: String? = body["accountId"]
        val playerCID: String? = body["playerCID"]
        if (key != "我已知晓") {
            return jackson.writeValueAsString(
                AEResponse(
                    errorCode = -1,
                    msg = "请远离闲鱼 该AE服务端项目已开源https://github.com/LLnutLL/AEAssistServer   登录key为:我已知晓"
                )
            )
        }
        log.info { "服务器接收到登录请求: $name|$serverName 已允许登录" }
        return jackson.writeValueAsString(AEResponse())
    }


}
