/*
 * Copyright (c) 2011-2023, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baomidou.mybatisplus.extension.parser.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.function.Consumer;

/**
 * jsqlparser 缓存 Fst 序列化 Caffeine 缓存实现
 *
 * @author miemie
 * @since 2023-08-05
 */
public class FstSerialCaffeineJsqlParseCache extends AbstractCaffeineJsqlParseCache {


    public FstSerialCaffeineJsqlParseCache(Cache<String, byte[]> cache) {
        super(cache);
    }

    public FstSerialCaffeineJsqlParseCache(Consumer<Caffeine<Object, Object>> consumer) {
        super(consumer);
    }

    @Override
    public byte[] serialize(Object obj) {
        return FstFactory.getDefaultFactory().asByteArray(obj);
    }

    @Override
    public Object deserialize(String sql, byte[] bytes) {
        try {
            return FstFactory.getDefaultFactory().asObject(bytes);
        } catch (Exception e) {
            cache.invalidate(sql);
            logger.error("deserialize error", e);
        }
        return null;
    }
}
