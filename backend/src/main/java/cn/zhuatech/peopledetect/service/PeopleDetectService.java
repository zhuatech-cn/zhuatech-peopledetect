/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.peopledetect.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class PeopleDetectService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result analyze(Request request) {
        List<ZoneResult> zones = request.zones().stream().map(zone -> {
            double occupancy = zone.capacity() == 0 ? 0 : zone.observedPeople() * 100.0 / zone.capacity();
            String level = occupancy >= request.criticalThreshold() ? "CRITICAL" : occupancy >= request.warningThreshold() ? "WARNING" : "NORMAL";
            return new ZoneResult(zone.zoneCode(), zone.zoneName(), zone.observedPeople(), zone.capacity(),
                Math.round(occupancy * 10.0) / 10.0, level,
                level.equals("CRITICAL") ? "限制进入并通知现场负责人" : level.equals("WARNING") ? "关注人员流入趋势" : "无需处置");
        }).toList();
        int total = zones.stream().mapToInt(ZoneResult::people).sum();
        long alertCount = zones.stream().filter(zone -> !zone.level().equals("NORMAL")).count();
        String status = !request.sourceAuthorized() ? "BLOCKED" : alertCount > 0 ? "ATTENTION" : "NORMAL";
        return new Result(status, total, alertCount, request.privacyMode(), request.privacyMode() ? "不保留原始画面，仅记录区域统计" : "演示模式不建议关闭隐私保护",
            zones, List.of("不做人脸识别或身份推断", "区域容量规则可审计", "生产环境应设置数据最小化和保留期限"), "LOCAL_ZONE_RULE_ENGINE");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotEmpty List<@Valid ZoneObservation> zones,
                          @DecimalMin("1") @DecimalMax("100") double warningThreshold,
                          @DecimalMin("1") @DecimalMax("100") double criticalThreshold,
                          boolean privacyMode,
                          boolean sourceAuthorized) {
        /**
         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
         */
        public Request {
            if (criticalThreshold < warningThreshold) throw new IllegalArgumentException("criticalThreshold must be >= warningThreshold");
        }
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ZoneObservation(@NotBlank String zoneCode, @NotBlank String zoneName,
                                  @Min(0) int observedPeople, @Min(1) int capacity) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ZoneResult(String zoneCode, String zoneName, int people, int capacity,
                             double occupancyPercent, String level, String action) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(String status, int totalPeople, long alertCount, boolean privacyMode,
                         String retentionPolicy, List<ZoneResult> zones, List<String> safeguards,
                         String executionMode) {}
}
