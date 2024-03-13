package com.curso.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.BatchOptions;
import org.redisson.client.codec.LongCodec;
import reactor.test.StepVerifier;

public class Lec13BatchTest extends BaseTest{

    @Test
    public void batchTest() {
        var batch = this.client.createBatch(BatchOptions.defaults());
        var list = batch.getList("numbers-list", LongCodec.INSTANCE);
        var set = batch.getSet("numbers-set", LongCodec.INSTANCE);

        for (long i = 0; i < 20_000; i++) {
            list.add(i);
            set.add(i);
        }

        StepVerifier.create(batch.execute().then())
                .verifyComplete();
    }
}
