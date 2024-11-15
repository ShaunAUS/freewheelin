package com.example.freewheelin.feature.problem.controller

import com.example.freewheelin.enums.Level
import com.example.freewheelin.enums.ProblemType
import com.example.freewheelin.feature.problem.api.ProblemService
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoList
import com.example.freewheelin.feature.problem.api.dto.ProblemSearchConditionDto
import com.example.freewheelin.global.response.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/problem")
class ProblemController(
    private val problemService: ProblemService
) {
    @GetMapping("")
    fun findProblems(
        @RequestParam("totalCount", required = false) totalCount: Int?,
        @RequestParam("unitCodeList", required = false) unitCodeList: List<String>?,
        @RequestParam("level", required = false) level: Level?,
        @RequestParam("problemType", required = false) problemType: ProblemType?
    ): ResponseEntity<Response<ProblemInfoList?>> {
        return ResponseEntity.ok(Response.success(problemService.findProblems(ProblemSearchConditionDto.of(totalCount, unitCodeList, level, problemType))))
    }
}