/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.client;

/**
 *
 * @author lone64dev
 */
public enum StepState {
    FORGE("포지를 설치하는 중..."),
    FABRIC("패브릭을 설치하는 중..."),
    READ("런처 파일를 불러오는 중..."),
    DL_LIBS("클라이언트 라이브러리들을 다운로드하는 중..."),
    DL_ASSETS("클라이언트 에셋들을 다운로드하는 중..."),
    EXTRACT_NATIVES("클라이언트 네이티브들의 압축을 푸는 중..."),
    MODS("클라이언트 모드들을 다운로드하는 중..."),
    EXTERNAL_FILES("외부 파일들을 다운로드하는 중..."),
    POST_EXECUTIONS("설치 후 게임을 실행합니다..."),
    MOD_LOADER("모드 로더를 설치하는 중..."),
    INTEGRATION("모드들을 통합하는 중..."),
    END("모든 과정을 완료했습니다!");

    final String details;

    StepState(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}