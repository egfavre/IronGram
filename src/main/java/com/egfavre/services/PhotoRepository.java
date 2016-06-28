package com.egfavre.services;

import com.egfavre.entities.Photo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 6/28/16.
 */
public interface PhotoRepository extends CrudRepository<Photo, Integer> {
}
