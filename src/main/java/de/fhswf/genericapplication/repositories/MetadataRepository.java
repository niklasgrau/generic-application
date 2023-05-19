package de.fhswf.genericapplication.repositories;

import de.fhswf.genericapplication.models.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository for {@link Metadata} model.
 *
 * @author Link, Kevin
 */
@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    /**
     * Returns the {@link Metadata} for the given version, if it exists.
     *
     * @param version Unique version to find the {@link Metadata}.
     * @return Optional of {@link Metadata}
     */
    Optional<Metadata> findByVersion(Long version);

    /**
     * Returns the newest {@link Metadata} available.
     *
     * @return Optional of the newest {@link Metadata}
     */
    Optional<Metadata> findFirstByOrderByCreatedAtDesc();

    /**
     * Deletes the {@link Metadata} related to the given version.
     *
     * @param version Unique version to find the {@link Metadata} to delete.
     */
    @Transactional
    void deleteByVersion(Long version);
}