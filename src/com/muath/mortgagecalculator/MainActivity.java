/**
File name: MainAcitivty.java
Author: Muath Alqurashi
Email Address: mhalqurashi@suffolk.edu
Last day of modification: Mar 10, 2015
Description: This app calculates the monthly payments of a house mortgage. 
It uses purchase price, down payment, and interest rate to calculate the 
payments.
*/
package com.muath.mortgagecalculator;
import android.app.Activity;
import android.os.Bundle;

import java.text.NumberFormat;

import android.text.Editable;
import android.text.TextWatcher;

import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.EditText;

import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	private static final NumberFormat currencyFormat = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat percentageFormat = NumberFormat
			.getPercentInstance();
		
	private double purchasePrice = 0.0;
	private double downPayment = 0.0;
	private double loanAmount = purchasePrice - downPayment;
	private double interestRate = 0.05;
	private int customPeriod = 5;
	private final int TEN_YEARS_PERIOD = 10;
	private final int TWINTY_YEARS_PERIOD = 20;
	private final int THIRTY_YEARS_PERIOD = 30;

	private TextView purchasePriceDisplayTextView;
	private TextView downPaymentDisplayTextView;
	private TextView loanAmountDisplayTextView;
	private TextView interestRateDisplayTextView;
	private TextView customPeriodTextView;
	private TextView customPeriodDisplayTextView;
	private TextView tenYearsDisplayTextView;
	private TextView twintyYearsDisplayTextView;
	private TextView thirtyYearsDisplayTextView;

	private EditText purchasePriceEditText;
	private EditText downPaymentEditText;
	private EditText interestRateEditText;

	private SeekBar customPeriodSeekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		purchasePriceDisplayTextView = 
				(TextView) findViewById(R.id.purchasePriceDisplayTextView);
		downPaymentDisplayTextView = 
				(TextView) findViewById(R.id.downPaymentDisplayTextView);
		loanAmountDisplayTextView = 
				(TextView) findViewById(R.id.loanAmountDisplayTextView);
		interestRateDisplayTextView =
				(TextView) findViewById(R.id.interestRateDisplayTextView);
		customPeriodTextView =
				(TextView) findViewById (R.id.customPeriodTextView);
		customPeriodDisplayTextView = 
				(TextView) findViewById (R.id.customPeriodDisplayTextView);
		tenYearsDisplayTextView =
				(TextView) findViewById (R.id.tenYearsDisplayTextView);
		twintyYearsDisplayTextView =
				(TextView) findViewById (R.id.twintyYearsDisplayTextView);
		thirtyYearsDisplayTextView = 
				(TextView) findViewById (R.id.thirtyYearsDisplayTextView);
		purchasePriceEditText =
				(EditText) findViewById (R.id.purchasePriceEditText);
		downPaymentEditText = 
				(EditText) findViewById (R.id.downPaymentEditText);
		interestRateEditText =
				(EditText) findViewById (R.id.interestRateEditText);
		customPeriodSeekBar = 
				(SeekBar) findViewById (R.id.customPeriodSeekBar);
		percentageFormat.setMinimumFractionDigits(2);
		purchasePriceDisplayTextView.setText
		(currencyFormat.format(purchasePrice));
		downPaymentDisplayTextView.setText
		(currencyFormat.format(downPayment));
		interestRateDisplayTextView.setText
		(percentageFormat.format(interestRate));
		updateLoanAmount();
		updateCustomPeriod();
		updateNonCustomPeriods();
		purchasePriceEditText.addTextChangedListener(
				purchasePriceEditTextWatcher);
		downPaymentEditText.addTextChangedListener(
				downPaymentEditTextWatcher);
		interestRateEditText.addTextChangedListener(
				interestRateEditTextWatcher);
		customPeriodSeekBar.setOnSeekBarChangeListener(
				customPeriodSeekBarListener);
		
	}
	
	private void updateLoanAmount () {
		loanAmount = purchasePrice - downPayment;
		loanAmountDisplayTextView.setText
		(currencyFormat.format(loanAmount));
	}
	
	private double monthlyPayments (int numOfPeriods) {
		double payments;
		double numerator; //The numerator of the loan payment formula.
		double denominator; //The denominator of the loan payment formula.
		numerator = (interestRate * loanAmount);
		denominator = 1 - Math.pow((1 + interestRate), -numOfPeriods);
		payments = numerator / denominator;
		return payments;
	}
	
	private void updateCustomPeriod () {
		String newText;
		newText = "For " + customPeriod + " Years:";
		customPeriodTextView.setText(newText);
		double payments;
		payments = monthlyPayments(customPeriod*12);
		customPeriodDisplayTextView.setText(currencyFormat.format(payments));
	}
	
	private void updateNonCustomPeriods () {
		double tenPayments, twintyPayments, thirtyPayments;
		tenPayments = monthlyPayments(TEN_YEARS_PERIOD*12);
		twintyPayments = monthlyPayments(TWINTY_YEARS_PERIOD*12);
		thirtyPayments = monthlyPayments(THIRTY_YEARS_PERIOD*12);
		tenYearsDisplayTextView.setText(currencyFormat.format(tenPayments));
		twintyYearsDisplayTextView.setText(
				currencyFormat.format(twintyPayments));
		thirtyYearsDisplayTextView.setText(
				currencyFormat.format(thirtyPayments));
	}
	
	private TextWatcher purchasePriceEditTextWatcher = new TextWatcher() {
		@Override
		public void onTextChanged (CharSequence s, int start, 
				int before, int count) {
			try {
				purchasePrice = Double.parseDouble(s.toString()) / 100;
			}
			catch (NumberFormatException e) {
				purchasePrice = 0.0;
			}
			purchasePriceDisplayTextView.setText
			(currencyFormat.format(purchasePrice));
			updateLoanAmount();
			updateCustomPeriod();
			updateNonCustomPeriods();
		}
		@Override
		public void afterTextChanged (Editable s)  {}
		@Override
		public void beforeTextChanged (CharSequence s, int start, int count, 
				int after) {}
	};
	
	private TextWatcher downPaymentEditTextWatcher = new TextWatcher () {
		@Override
		public void onTextChanged (CharSequence s, int start, int before,
				int count) {
			try {
				downPayment = Double.parseDouble(s.toString()) / 100;
			}
			catch(NumberFormatException e) {
				downPayment = 0.0;
			}
			downPaymentDisplayTextView.setText(
					currencyFormat.format(downPayment));
			updateLoanAmount();
			updateCustomPeriod();
			updateNonCustomPeriods();	
		}
		@Override
		public void afterTextChanged(Editable s) {}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}
	};
	
	private TextWatcher interestRateEditTextWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			try {
				interestRate = (Double.parseDouble(s.toString()) / 100) / 100;
			}
			catch (NumberFormatException e) {
				interestRate = 0.05;
			}
			interestRateDisplayTextView.setText(
					percentageFormat.format(interestRate));
			updateCustomPeriod();
			updateNonCustomPeriods();
		}
		@Override
		public void afterTextChanged(Editable s) {}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}
	};
	
	private OnSeekBarChangeListener customPeriodSeekBarListener = 
			new OnSeekBarChangeListener () {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			customPeriod = progress + 1; 
			//The added one is to set the minimum value to 1. 
			updateCustomPeriod();
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {	
		}
	};
	
	
}
