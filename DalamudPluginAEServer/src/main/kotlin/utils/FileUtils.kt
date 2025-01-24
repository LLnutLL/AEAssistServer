package xx.xxxx.utils

import java.net.URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * @author Hello
 * create on 2025.01.24 19:44
 */
object FileUtils {
    fun getCurrentPath(clazz: Class<*>): String {
        val location: URL = clazz.getProtectionDomain().codeSource.location
        var path = location.path
        if (path.contains("jar")) {
            val i = path.indexOf("jar")
            path = path.substring(0, i)
        }
        var filePath: String? = path.substring(path.indexOf("/"), path.lastIndexOf("/") + 1)

        filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8)
        return filePath
    }
}