package com.bill.ChatroomAdvanced.exception;

import com.bill.ChatroomAdvanced.bean.Alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AlertException extends Exception {

	private Alert msg;
}
