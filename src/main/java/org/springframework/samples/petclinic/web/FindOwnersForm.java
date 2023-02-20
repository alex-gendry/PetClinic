
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
import org.springframework.samples.petclinic.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * JavaBean Form controller that is used to search for <code>Owner</code>s by
 * last name.
 * 
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class FindOwnersForm {

	private final Clinic clinic;


	@Autowired
	public FindOwnersForm(Clinic clinic) {
		this.clinic = clinic;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/owners/search", method = RequestMethod.GET)
	public String setupForm(Model model) {
		model.addAttribute("owner", new Owner());
		return "owners/search";
	}

	@RequestMapping(value = "/owners", method = RequestMethod.GET)
	public String processSubmit(Owner owner, BindingResult result, Model model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Owner> results = this.clinic.findOwners(owner.getLastName());
		if (results.size() < 1) {
			// no owners found
			result.rejectValue("lastName", "notfound", new Owner[]{owner}, "not found"); // should trigger poor validation sink
			return "owners/search";
		}
		if (results.size() > 1) {
			// multiple owners found
			model.addAttribute("selections", results);
			return "owners/list";
		}
		else {
			// 1 owner found
			callingNonPublicMethod(owner, result); // added to test RequestMapping rules
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();   // this should trigger Open Redirect sink ?
		}
	}

	// This should not be flagged as entry point
	private void callingNonPublicMethod(Owner owner, BindingResult result) {
	     result.rejectValue("lastName", "found", new Owner[]{owner}, "1 found"); // should not trigger sink as entry point method
	}
	
	// This should be considered as entry point - test RequestParam 
	public void testAnnotatedParameterMethod(@RequestParam(value="id", required = false) String ownerId, String ownerName, ServletResponse response) throws IOException {
	    ServletOutputStream outStr = response.getOutputStream();
	    outStr.print(ownerId);
		outStr.print(ownerName);
	}
	
	// should not be flagged ?
	@RequestMapping(value = "/testreturn")
	public String testReturn(String ownerName) {  
	     return ownerName;
	}
}
