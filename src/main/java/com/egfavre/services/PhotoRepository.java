package com.egfavre.services;

import com.egfavre.entities.Photo;
import com.egfavre.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 6/28/16.
 */
public interface PhotoRepository extends CrudRepository<Photo, Integer> {
    public Iterable<Photo> findByRecipient(User recipient);
    public Iterable<Photo> findByShow(String show);
    public Iterable<Photo> findBySenderAndShow(User sender, String show);
}
