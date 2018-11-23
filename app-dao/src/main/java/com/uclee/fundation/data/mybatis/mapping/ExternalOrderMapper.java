package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.ExternalOrder;
import com.uclee.fundation.data.mybatis.model.ExternalOrderItem;

import java.util.List;

public interface ExternalOrderMapper {
    ExternalOrder CreateOutOrder(ExternalOrder externalOrder);
    ExternalOrderItem AddOutOrderItem(ExternalOrderItem externalOrderItem);
}
