package com.arenabooking.arena.Service;

import com.arenabooking.arena.Model.Arena;
import com.arenabooking.arena.Repository.ArenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArenaService {


    @Autowired
    ArenaRepository arenaRepository;

    public void save(Arena booking) {
        arenaRepository.save(booking);
    }

    public List<Arena> listAll() {
        return arenaRepository.findAll();
    }

    public Optional<Arena> findClientById(Long id) {
        return arenaRepository.findById(id);
    }

}
