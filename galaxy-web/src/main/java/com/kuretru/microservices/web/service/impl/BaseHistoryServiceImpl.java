package com.kuretru.microservices.web.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuretru.microservices.web.entity.data.BaseHistoryDO;
import com.kuretru.microservices.web.entity.transfer.BaseHistoryDTO;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.microservices.web.service.BaseHistoryService;
import org.mapstruct.Mapping;

import java.time.Instant;

/**
 * 历史数据型业务逻辑层基类，继承此类即可获得一些基础方法
 * 泛型：M->实体对应的数据访问层，D->实体对应的数据对象，T->实体对应的数据传输对象，Q->实体对应的查询对象
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public abstract class BaseHistoryServiceImpl<M extends BaseMapper<D>, D extends BaseHistoryDO, T extends BaseHistoryDTO> implements BaseHistoryService<T> {

    protected final M mapper;
    protected final BaseHistoryEntityMapper<D, T> entityMapper;

    public BaseHistoryServiceImpl(M mapper, BaseHistoryEntityMapper<D, T> entityMapper) {
        this.mapper = mapper;
        this.entityMapper = entityMapper;
    }

    @Override
    public void save(T record) throws ServiceException {
        verifyDTO(record);
        D data = entityMapper.dtoToDo(record);
        data.setCreateTime(Instant.now());
        mapper.insert(data);
    }

    protected void verifyDTO(T record) throws ServiceException {

    }

    public interface BaseHistoryEntityMapper<D extends BaseHistoryDO, T extends BaseHistoryDTO> {

        /**
         * 将数据传输实体转换为数据实体
         *
         * @param record 数据传输实体
         * @return 数据实体
         */
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createTime", ignore = true)
        D dtoToDo(T record);

    }

}