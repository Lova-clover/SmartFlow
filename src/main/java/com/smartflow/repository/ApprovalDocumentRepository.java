package com.smartflow.repository;

import com.smartflow.domain.ApprovalDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalDocumentRepository extends JpaRepository<ApprovalDocument, Long> {
    @Query("""
      SELECT d 
      FROM ApprovalDocument d 
      WHERE d.authorUsername = :username OR d.shared = true
    """)
    List<ApprovalDocument> findVisibleDocs(@Param("username") String username);

    // 검색어 O + 공개문서 포함
    @Query("""
        SELECT d FROM ApprovalDocument d
        WHERE (d.authorUsername = :username OR d.shared = true)
        AND (d.title LIKE %:keyword% OR d.content LIKE %:keyword%)
    """)
    Page<ApprovalDocument> findWithSharedAndKeyword(@Param("username") String username,
                                                    @Param("keyword") String keyword,
                                                    Pageable pageable);

    // 검색어 O + 공개문서 제외
    @Query("""
        SELECT d FROM ApprovalDocument d
        WHERE d.authorUsername = :username
        AND (d.title LIKE %:keyword% OR d.content LIKE %:keyword%)
    """)
    Page<ApprovalDocument> findWithoutSharedAndKeyword(@Param("username") String username,
                                                       @Param("keyword") String keyword,
                                                       Pageable pageable);

    // 검색어 X + 공개문서 포함
    @Query("""
        SELECT d FROM ApprovalDocument d
        WHERE d.authorUsername = :username OR d.shared = true
    """)
    Page<ApprovalDocument> findWithShared(@Param("username") String username, Pageable pageable);

    // 검색어 X + 공개문서 제외
    @Query("""
        SELECT d FROM ApprovalDocument d
        WHERE d.authorUsername = :username
    """)
    Page<ApprovalDocument> findWithoutShared(@Param("username") String username, Pageable pageable);

}
