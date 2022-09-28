
package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.Collection;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.PetType;
import org.springframework.samples.petclinic.validation.PetValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.beans.factory.annotation.Value;

/**
 * JavaBean Form controller that is used to edit an existing <code>Pet</code>.
 * 
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/*/pets/{petId}/edit")
@SessionAttributes("pet")
public class EditPetForm {

	private final Clinic clinic;
	private @Value("${jdbc.password}") String petAccess;


	@Autowired
	public EditPetForm(Clinic clinic) {
		this.clinic = clinic;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.clinic.getPetTypes();
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(@PathVariable("petId") int petId, Model model) {
		Pet pet = this.clinic.loadPet(petId);
		model.addAttribute("pet", pet);  // currently pass through should this be Trust Boundary Violation ?
		return "pets/form";
	}

	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST })
	public String processSubmit(@ModelAttribute("pet") Pet pet, BindingResult result, SessionStatus status, ServletResponse response) throws IOException {
		new PetValidator().validate(pet, result);
		// For testing purposes: only call callPublicMethod on petId 5
		if (pet.getId() == 5) {
			callPublicMethod(String.valueOf(pet.getOwner().getId()), pet.getName(), response);
		}
		if (result.hasErrors()) {
			return "pets/form";
		}
		else {
			this.clinic.storePet(pet);   
			status.setComplete();
			return "redirect:/owners/" + pet.getOwner().getId();   // should this be a sink ?
		}
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String deletePet(@PathVariable int petId) {
		Pet pet = this.clinic.loadPet(petId);
		this.clinic.deletePet(petId);
		return "redirect:/owners/" + pet.getOwner().getId();
	}
	
     // This should be considered as entry point - test non annotated public method 
	public void callPublicMethod(String petId, String petName, ServletResponse response) throws IOException {
	    ServletOutputStream outStr = response.getOutputStream();
		response.setContentType("text/html");

		outStr.print("<html><body>");
		outStr.print("<p>PetID: " + petId + "\n</p>");
		outStr.print("<p>PetName: " + petName + "</p>");
		outStr.print("</b></body></html>");

		outStr.flush();
	}
	
	// This should concatenate with the class Url when reporting external entry url
	@RequestMapping(value = "/concatenateMethodUrl")
	public void testUrlConcatenate(String petId, String petName, ServletResponse response) throws IOException {
	    ServletOutputStream outStr = response.getOutputStream();
		response.setContentType("text/html");

		outStr.print("<html><body>");
		outStr.print("<p>PetID: " + petId + "\n</p>");
		outStr.print("<p>PetName: " + petName + "</p>");
		outStr.print("</b></body></html>");
	}
	
	// @Value - privacy violation - incoming parameter and field access
	void checkforPrivacyViolation(@Value("#{secret.password}") String secretAccess, ServletResponse response) throws IOException {
      ServletOutputStream outStr = response.getOutputStream();
	  outStr.print(secretAccess); // Incoming param @Value with EL matching .*password.*
	  outStr.print(petAccess);   // Field access @Value with EL matching .*password.*
	}

}
