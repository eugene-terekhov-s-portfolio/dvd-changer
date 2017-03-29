package ru.nergal;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.nergal.domain.*;

@Controller(value = "/")
public class RootController {
	private UserRepository ur;
	private DiskRepository dr;
	private TakenItemRepository tir;

	@Autowired
	public RootController(UserRepository ur, DiskRepository dr, TakenItemRepository tir) {
		this.ur = ur;
		this.dr = dr;
		this.tir = tir;
	}

	@RequestMapping(value = {"/", "/home"})
	public String showHomePage() {
		return "home";
	}


    @RequestMapping(value = "/collection")
    public String showHomePage(Model model) {
        User user = ur.findOne((long) 1);
		model.addAttribute("user", user);
		model.addAttribute("takenByMe", tir.findByTenant(user));
        return "collection";
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

    @RequestMapping(value="/takenDisks", method=RequestMethod.GET)
    public String showTakenDisks(Model model) {
    	model.addAttribute("takenDisks", tir.findAll());
    	return "takenDisks";
    }

	@RequestMapping(value="/freeDisks", method = RequestMethod.GET)
	public String showFreeDisks(Model model) {
		User user = ur.findOne((long)1);
		model.addAttribute("freeDisks", dr.findAllFree(user));
		return "freeDisks";
	}

	@RequestMapping(value = "/takeDisk", method = RequestMethod.POST)
	public String takeDisk(@RequestParam(value = "diskId") long diskId) {
		Disk disk = dr.findOne(diskId);
		User user = ur.findOne((long)1);
		TakenItem ti = new TakenItem();
		ti.setDisk(disk);
		ti.setTenant(user);
		tir.save(ti);

		return "redirect:/collection";
	}

	@RequestMapping(value = "/returnDisk", method = RequestMethod.POST)
	public String returnDisk(@RequestParam(value = "tiId") long tiId) {
		tir.delete(tir.findOne(tiId));
		return "redirect:/collection";
	}

	@RequestMapping(value={"/login"})
	public String login(){
		return "login";
	}
}
