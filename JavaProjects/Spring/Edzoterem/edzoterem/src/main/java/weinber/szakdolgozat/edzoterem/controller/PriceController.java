package weinber.szakdolgozat.edzoterem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import weinber.szakdolgozat.edzoterem.entity.Ticket;
import weinber.szakdolgozat.edzoterem.entity.User;
import weinber.szakdolgozat.edzoterem.service.TicketServiceIF;
import weinber.szakdolgozat.edzoterem.user.CrmTicket;

@Controller
@RequestMapping("/weinbergym")
public class PriceController {

	@Autowired
	private TicketServiceIF ticketService;
	
	/**
	 * Handles the prices page.
	 * Called on header.
	 * @param model
	 * @return Prices page.
	 */
	@GetMapping("/prices")
	public String prices(Model model) {
		
		// 1 id napijegy
		Ticket dailyTicket=ticketService.findDailyTicket();
		
		List<Ticket> ticketList=ticketService.findAll();
		ticketList.remove(dailyTicket);
		
		model.addAttribute("dailyTicket", dailyTicket);
		model.addAttribute("ticketList", ticketList);
		
		
		return "prices";
	}
	
	@GetMapping("/addTicket")
	public String addTicket(Model model) {
		
		CrmTicket crmTicket=new CrmTicket();
		
		
		model.addAttribute("ticket", crmTicket);
		
		return "add-ticket";
	}
//	
	
	@PostMapping("/ticketAddingDetailsProcessing")
	public String addTicket(@Valid@ModelAttribute("ticket") CrmTicket crmTicket,BindingResult bindingResult ,Model model) {
		
		
		if(bindingResult.hasErrors()) {
			return "add-ticket";
		}
		
		Ticket existingTicket=ticketService.findByType(crmTicket.getType());
		if(existingTicket != null) {
			String error="Ez a típus megnevezés már használatban van!";
			model.addAttribute("nameError", error);
			return "add-ticket";
		}

		Ticket newTicket=ticketService.convertCrmTicketToTicket(null,crmTicket);
		
		ticketService.save(newTicket);
		
		return "redirect:/weinbergym/prices";
	}
	
	@GetMapping("/ticketModificationProcess")
	public String modifyTicket(@RequestParam("ticketId") int ticketId,Model model) {
		
		Ticket ticket=ticketService.findById(ticketId);
		
		CrmTicket crmTicket=ticketService.convertTicketToCrmTicket(ticket);
		
		model.addAttribute("ticket", crmTicket);
		model.addAttribute("originalTicketId", ticket.getId());
		
		
		return "modify-ticket";
	}
	
	@PostMapping("/ticketModificationDetailsProcessing/{originalTicketId}")
	public String modificationProcessTicket(@Valid@ModelAttribute("ticket") CrmTicket crmTicket,BindingResult bindingResult , @PathVariable int originalTicketId,
			Model model) {
		
		
		if(bindingResult.hasErrors()) {
			return "modify-ticket";
		}
		
		Ticket existingTicket=ticketService.findByType(crmTicket.getType());
		if(existingTicket != null && existingTicket.getId()!=originalTicketId) {
			String error="Ez a típus megnevezés már használatban van!";
			model.addAttribute("nameError", error);
			return "modify-ticket";
		}

		Ticket newTicket=ticketService.findById(originalTicketId);
		
		 newTicket=ticketService.convertCrmTicketToTicket(newTicket,crmTicket);
		
		ticketService.save(newTicket);
		
		return "redirect:/weinbergym/prices";
	}
	
	@GetMapping("/ticketDeletion")
	public String deleteTicket(@RequestParam int ticketId, Model model) {
		
		Ticket ticket=ticketService.findById(ticketId);
		

		ticketService.setTicketsUserListToDailyTicket(ticket);

		ticketService.deleteTicket(ticket);
		
		return "redirect:/weinbergym/prices";
	}
	
}
