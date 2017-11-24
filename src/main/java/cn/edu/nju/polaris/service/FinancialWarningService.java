package cn.edu.nju.polaris.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by 费慧通 on 2017/11/3.
 */
@CacheConfig(cacheNames = "warnings")
public interface FinancialWarningService {
    /**
     * 得到财务预警信息
     * @param company_id 公司id
     * @param phase 时间
     * @return [盈利能力、偿债能力、营运能力、成长能力、总评分]
     */
    @Cacheable
    public double[] getWarningMessage(long company_id, String phase);
}
