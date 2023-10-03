package com.example.demo.moim.repository;

import com.example.demo.moim.entity.*;
import com.example.demo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MoimRepository extends JpaRepository<Moim, Long>, JpaSpecificationExecutor<Moim> {
    @Query("select m from Moim m " +
            "join fetch m.moimPaymentInfo " +
            "join fetch m.participantsInfo " +
            "join fetch m.contents " +
            "join fetch m.destination " +
            "join fetch m.state " +
            "left join fetch m.participantsInfo.participants " +
            "where m.id=:id")
    Optional<Moim> findById(Long id);

    @Query(value = "select m from Moim m", countQuery = "select count(m) from Moim m")
    List<Moim> findRecentPageableMoim(Pageable pageable);

    @Query("select m from Moim m join fetch m.moimPaymentInfo where m.id=:moimId")
    Optional<Moim> findByIdJoinFetchPaymentInfo(Long moimId);

    @Query("select m from Moim m left join fetch m.boards where m.id=:moimId")
    Moim findByIdWithBoards(Long moimId);

    @Query("select distinct m from Moim m join m.participantsInfo.participants p " +
            "where p.user = :user")
    Page<Moim> findMyMoim(@Param("user") User user, Pageable pageable);

    @Query("select o from MoimOption o where o.moimDestination.moim=:moim")
    List<MoimOption> findOptionListByMoim(Moim moim);

    @Query("select p from Participant p join fetch p.user where p.moimParticipants.moim=:moim")
    List<Participant> findParticipantListByMoim(Moim moim);

    @Query("select distinct m from Moim m join m.participantsInfo.participants p " +
            "where p.user.id = :userId")
    Page<Moim> findAllByUserId(Long userId, Pageable pageable);

    @Query(value = "select distinct m from Moim m where m.destination.city=:city")
    Page<Moim> findAllByCity(String city, Pageable pageable);

    @Query("select distinct m.destination.country from Moim m")
    List<String> findAllCountyList();

    @Query("select distinct m.destination.city from Moim m")
    List<String> findAllCityList();

    @Query("select distinct m from Moim m where m.moimPaymentInfo.amountInstallment > :min and m.moimPaymentInfo.amountInstallment < :max")
    Page<Moim> findAllByPrice(Integer max, Integer min, Pageable pageable);
    @Query("select distinct m from Moim m join  m.destination md join md.moimOptions mo where mo.category=:category")
    Page<Moim> findAllByOption(String category, Pageable pageable);

    @Query("select m from Moim m join fetch m.state join fetch m.participantsInfo pi join fetch pi.participants where m.state.state=:state")
    List<Moim> findAllByState(StateType state);

    @Query("select m from Moim m join fetch m.destination left join fetch m.destination.moimOptions where m.id=:moimId")
    Moim findOptionListByMoimId(Long moimId);
}
