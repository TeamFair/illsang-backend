package com.illsang.quest.controller.quest

import com.illsang.quest.dto.request.quest.StoreCreateRequest
import com.illsang.quest.dto.request.quest.StoreUpdateRequest
import com.illsang.quest.dto.response.quest.StoreResponse
import com.illsang.quest.service.quest.StoreService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("/api/v1/store")
@Tag(name = "store", description = "상점")
class StoreController(
    private val storeService: StoreService
) {

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(operationId = "STO001", summary = "상점 상세 조회")
    fun get(@PathVariable id: Long): ResponseEntity<StoreResponse> {
        val store = storeService.getStore(id)
        return ResponseEntity.ok(StoreResponse.from(store))
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(operationId = "STO002", summary = "상점 리스트 조회")
    fun getAll(
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<StoreResponse>> {
        val stores = storeService.getStoreList(pageable)
        return ResponseEntity.ok(stores.map(StoreResponse::from))
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "STO003", summary = "상점 생성")
    fun create(@RequestBody request: StoreCreateRequest): ResponseEntity<StoreResponse> {
        val model = storeService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            StoreResponse.from(model)
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "STO004", summary = "상점 정보 수정")
    fun update(
        @PathVariable id: Long, @RequestBody request: StoreUpdateRequest
    ): ResponseEntity<StoreResponse> {
        val store = storeService.update(id, request)
        return ResponseEntity.ok(StoreResponse.from(store))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "STO003", summary = "상점 삭제")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        storeService.delete(id)
        return ResponseEntity.ok().build()
    }
}