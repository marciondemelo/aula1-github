package model.service;

import model.entitties.CarRental;
import model.entitties.Invoice;

public class RentalService {
	private Double pricePerDay;
	private Double pricePerHour;
	
	private TaxService taxService;

	public RentalService() {		
	}
	
	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		super();
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime(); //milessegundos
		long t2 = carRental.getFinish().getTime();//milessegundos
		// divide por 1000 para converter em segundos
		// divide por 60 para converter para minutos
		//divide por 60 para converter para horas
		double hours = (double)(t2 - t1) / 100 / 60 / 60;
		double basicPayment;
		if(hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}
