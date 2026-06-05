package com.konradjurkowski.moviehub_server.feature.upload.controller

import com.konradjurkowski.moviehub_server.core.service.cloudinary.CloudinaryService
import com.konradjurkowski.moviehub_server.feature.upload.model.dto.UploadResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/uploads")
class UploadController(
    private val cloudinaryService: CloudinaryService,
) {

    @PostMapping("/comment-image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadCommentImage(@RequestParam("file") file: MultipartFile): UploadResponse {
        val result = cloudinaryService.upload(file = file, folder = COMMENTS_FOLDER)
        return UploadResponse(url = result.url, publicId = result.publicId)
    }

    companion object {
        private const val COMMENTS_FOLDER = "comments"
    }
}
