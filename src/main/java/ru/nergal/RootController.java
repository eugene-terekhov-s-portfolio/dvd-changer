package ru.nergal;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.nergal.domain.UserRepository;
import ru.nergal.domain.DiskRepository;
import ru.nergal.domain.Disk;

/**
 * Created by terekhov-ev on 24.03.2017.
 */
@Controller(value = "/")
public class RootController {
	private UserRepository ur;
	private DiskRepository dr;

	@Autowired
	public RootController(UserRepository ur, DiskRepository dr) {
		this.ur = ur;
		this.dr = dr;
	}


    @RequestMapping(value = "/")
    public String showHomePage(Model model) {
        model.addAttribute("user", ur.findOne((long)1));
        return "home";
    }

    @RequestMapping(value = "/newDisk", method=RequestMethod.GET) 
    public String showNewDiskForm(Model model) {
		model.addAttribute("user", ur.findOne((long)1));
		return "newDisk";
    }

    @RequestMapping(value = "/newDisk", method=RequestMethod.POST) 
    public String createNewDisk(
    		@RequestParam(value="owner_id") Long userId,
    		@RequestParam(value="movie_title") String movieTitle) {
		Disk disk = new Disk();
		disk.setMovieTitle(movieTitle);
		disk.setOwner(ur.findOne(userId));
		dr.save(disk);
		return "redirect:/";
    }    
}
