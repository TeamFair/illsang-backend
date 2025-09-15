package com.illsang.management.controller

import com.illsang.common.enums.TitleType
import com.illsang.management.dto.response.TitleResponse
import com.illsang.management.service.TitleService
import com.illsang.management.dto.request.TitleRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("/api/v1/title")
@Tag(name = "Title", description = "칭호")
class TitleController(
    private val titleService: TitleService
) {

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(operationId = "TTL001", summary = "칭호 단일 조회")
    fun getTitle(@PathVariable id: String): ResponseEntity<TitleResponse> {

        val title = titleService.getTitle(id)

        return ResponseEntity.ok(TitleResponse.from(title))
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(operationId = "TTL002", summary = "칭호 전체 조회")
    fun getAllTitles(): ResponseEntity<List<TitleResponse>> {
        val titles = titleService.getAllTitles()
        return ResponseEntity.ok(titles.map(TitleResponse::from))
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "TTL003", summary = "칭호 수정")
    fun updateTitle(
        @PathVariable id: String,
        @RequestBody request: TitleRequest
    ): ResponseEntity<TitleResponse> {
        val title = titleService.updateTitle(id, request)
        return ResponseEntity.ok(TitleResponse.from(title))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "TTL004", summary = "칭호 삭제")
    fun deleteTitle(@PathVariable id: String): ResponseEntity<Void> {
        titleService.deleteTitle(id)
        return ResponseEntity.ok().build()
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "TTL005", summary = "칭호 생성")
    fun createTitle(@RequestBody request: TitleRequest): ResponseEntity<TitleResponse> {
        val title = titleService.createTitle(request)

        return ResponseEntity.ok(TitleResponse.from(title))
    }

    @GetMapping("/season/title/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "TTL006", summary = "시즌  보상 조회")
    fun getSeasonTitle(@PathVariable type: TitleType) : ResponseEntity<List<TitleResponse>>{
        val titles = titleService.getSeasonTitles(type)
        return ResponseEntity.ok(titles.map { TitleResponse.from(it) })
    }
}