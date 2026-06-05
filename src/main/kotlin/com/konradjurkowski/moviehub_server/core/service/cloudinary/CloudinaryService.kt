package com.konradjurkowski.moviehub_server.core.service.cloudinary

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.konradjurkowski.moviehub_server.core.model.dto.response.ErrorCode
import com.konradjurkowski.moviehub_server.core.utils.exceptions.ApiException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CloudinaryService(
    private val cloudinary: Cloudinary,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun upload(file: MultipartFile, folder: String): UploadResult {
        if (file.isEmpty || file.contentType !in ALLOWED_CONTENT_TYPES) {
            throw ApiException(ErrorCode.INVALID_FILE_TYPE)
        }
        if (file.size > MAX_FILE_SIZE_BYTES) {
            throw ApiException(ErrorCode.FILE_TOO_LARGE)
        }

        val result = try {
            cloudinary.uploader().upload(
                file.bytes,
                ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", "image",
                ),
            )
        } catch (exception: Exception) {
            logger.error("Cloudinary upload failed", exception)
            throw ApiException(ErrorCode.FILE_UPLOAD_FAILED)
        }

        return UploadResult(
            url = result["secure_url"] as String,
            publicId = result["public_id"] as String,
        )
    }

    fun destroy(publicId: String) {
        runCatching {
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "image"))
        }.onFailure { exception ->
            logger.warn("Cloudinary destroy failed for publicId=$publicId", exception)
        }
    }

    companion object {
        private const val MAX_FILE_SIZE_BYTES = 10L * 1024 * 1024
        private val ALLOWED_CONTENT_TYPES = setOf(
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/gif",
        )
    }
}

data class UploadResult(
    val url: String,
    val publicId: String,
)
