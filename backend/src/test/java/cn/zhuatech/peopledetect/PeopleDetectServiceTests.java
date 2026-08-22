/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.peopledetect;

import cn.zhuatech.peopledetect.service.PeopleDetectService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PeopleDetectServiceTests {
    private final PeopleDetectService service = new PeopleDetectService();

    @Test void flagsCrowdedZone() {
        var zones = List.of(new PeopleDetectService.ZoneObservation("A", "等候区", 18, 20), new PeopleDetectService.ZoneObservation("B", "服务区", 6, 15));
        var result = service.analyze(new PeopleDetectService.Request(zones, 70, 90, true, true));
        assertThat(result.status()).isEqualTo("ATTENTION");
        assertThat(result.alertCount()).isEqualTo(1);
        assertThat(result.zones().getFirst().level()).isEqualTo("CRITICAL");
    }

    @Test void blocksUnauthorizedSource() {
        var zones = List.of(new PeopleDetectService.ZoneObservation("A", "大厅", 3, 20));
        assertThat(service.analyze(new PeopleDetectService.Request(zones, 70, 90, true, false)).status()).isEqualTo("BLOCKED");
    }
}
