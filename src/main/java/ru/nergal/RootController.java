package ru.nergal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.nergal.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller(value = "/")
public class RootController {
	private UserRepository ur;
	private DiskRepository dr;
	private TakenItemRepository tir;
	private MovieRepository mr;

	@Autowired
	public RootController(UserRepository ur, DiskRepository dr, 
						  TakenItemRepository tir, MovieRepository mr) {
		this.ur = ur;
		this.dr = dr;
		this.tir = tir;
		this.mr = mr;
	}

	@RequestMapping(value = {"/", "/home"})
	public String showHomePage() {
		return "home";
	}


    @RequestMapping(value = "/collection")
    public String showHomePage(Model model, Principal principal) {
        User user = ur.findByLogin(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("takenByMe", tir.findByTenant(user));
        return "collection";
    }

    @RequestMapping(value = "/newDisk", method=RequestMethod.GET) 
    public String showNewDiskForm(Model model, Principal principal) {
		User user = ur.findByLogin(principal.getName());
		model.addAttribute("user", user);
		return "newDisk";
    }

    @RequestMapping(value = "/newDisk", method=RequestMethod.POST) 
    public String createNewDisk(
    		@RequestParam(value="owner_id") Long userId,
    		@RequestParam(value="movieId") Long movieId,
    		@RequestParam(value="movieTitle") String movieTitle,
    		@RequestParam(value="originalTitle") String originalTitle,
    		@RequestParam(value="posterPath") String posterPath,
			@RequestParam(value="overview") String overview,
			@RequestParam(value="releaseYear") int releaseYear,
			@RequestParam(value="rating") double rating
    		) {
		Movie movie = new Movie(movieId, movieTitle, originalTitle, 
			                    posterPath, overview, releaseYear, rating);
		mr.save(movie);
		Disk disk = new Disk(movie, ur.findOne(userId));
		dr.save(disk);
		return "redirect:/collection";
    }

    @RequestMapping(value="/takenDisks", method=RequestMethod.GET)
    public String showTakenDisks(Model model, Principal principal) {
    	model.addAttribute("takenDisks", tir.findAll());
		model.addAttribute("user", ur.findByLogin(principal.getName()));
    	return "takenDisks";
    }

	@RequestMapping(value="/freeDisks", method = RequestMethod.GET)
	public String showFreeDisks(Model model, Principal principal) {
		User user = ur.findByLogin(principal.getName());
		model.addAttribute("freeDisks", dr.findAllFree(user));
		model.addAttribute("user", user);
		return "freeDisks";
	}

	@RequestMapping(value = "/takeDisk", method = RequestMethod.POST)
	public String takeDisk(@RequestParam(value = "diskId") long diskId,
						   Principal principal) {
		Disk disk = dr.findOne(diskId);
		User user = ur.findByLogin(principal.getName());
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

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterForm() {
		return "register";
	}

	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String createNewUser(@RequestParam(value = "username") String userName,
								@RequestParam(value = "login") String login,
								@RequestParam(value = "password") String password) {
		User user = ur.findByLogin(login);
		if (user != null) {
			return "redirect:/register?error";
		}
		user = new User(userName, login, password);
		ur.save(user);
		return "redirect:/collection";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
}
