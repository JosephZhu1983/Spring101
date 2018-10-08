package me.josephzhu.spring101data;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface InvestOrderRepository extends PagingAndSortingRepository<InvestOrder, Long> {
    @Query("select * from INVEST_ORDER where status=:status limit 3")
    Stream<InvestOrder> findTop3ByStatusIs(@Param("status") int status);
}
