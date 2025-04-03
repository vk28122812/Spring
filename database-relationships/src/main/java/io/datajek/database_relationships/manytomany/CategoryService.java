package io.datajek.database_relationships.manytomany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    public List<Category> allCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategory(int id){
        return categoryRepository.findById(id).get();
    }

    public Category addCategory(Category category){
        category.setId(0);
        return categoryRepository.save(category);
    }

    public void deleteCategory(int id){
        categoryRepository.deleteById(id);
    }

//    @Transactional
//    public void deleteCategory(int id){
//        Category category = categoryRepository.findById(id).get();
//        if(category != null){
//            List<Tournament> tournaments = category.getTournaments();
//            for(Tournament tournament : tournaments){
//                tournament.getPlayingCategories().remove(category);
//                tournamentRepository.save(tournament);
//            }
//        }
//        categoryRepository.delete(category);
//    }



}
