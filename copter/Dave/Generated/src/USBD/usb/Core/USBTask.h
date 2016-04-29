/*
  Copyright 2013  Dean Camera (dean [at] fourwalledcubicle [dot] com)

  Permission to use, copy, modify, distribute, and sell this
  software and its documentation for any purpose is hereby granted
  without fee, provided that the above copyright notice appear in
  all copies and that both that the copyright notice and this
  permission notice and warranty disclaimer appear in supporting
  documentation, and that the name of the author not be used in
  advertising or publicity pertaining to distribution of the
  software without specific, written prior permission.

  The author disclaims all warranties with regard to this
  software, including all implied warranties of merchantability
  and fitness.  In no event shall the author be liable for any
  special, indirect or consequential damages or any damages
  whatsoever resulting from loss of use, data or profits, whether
  in an action of contract, negligence or other tortious action,
  arising out of or in connection with the use or performance of
  this software.
*/
/*******************************************************************************
 Copyright (c) 2014, Infineon Technologies AG                                 **
 All rights reserved.                                                         **
                                                                              **
 Redistribution and use in source and binary forms, with or without           **
 modification,are permitted provided that the following conditions are met:   **
                                                                              **
 *Redistributions of source code must retain the above copyright notice,      **
 this list of conditions and the following disclaimer.                        **
 *Redistributions in binary form must reproduce the above copyright notice,   **
 this list of conditions and the following disclaimer in the documentation    **
 and/or other materials provided with the distribution.                       **
 *Neither the name of the copyright holders nor the names of its contributors **
 may be used to endorse or promote products derived from this software without**
 specific prior written permission.                                           **
                                                                              **
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"  **
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE    **
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE   **
 ARE  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE   **
 LIABLE  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR         **
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF         **
 SUBSTITUTE GOODS OR  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS    **
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN      **
 CONTRACT, STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)       **
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE   **
 POSSIBILITY OF SUCH DAMAGE.                                                  **
                                                                              **
 To improve the quality of the software, users are encouraged to share        **
 modifications, enhancements or bug fixes with Infineon Technologies AG       **
 dave@infineon.com).                                                          **
                                                                              **
********************************************************************************
**                                                                            **
**                                                                            **
** PLATFORM : Infineon XMC4000 Series                                         **
**                                                                            **
** COMPILER : Compiler Independent                                            **
**                                                                            **
** AUTHOR : App Developer                                                     **
**                                                                            **
** MAY BE CHANGED BY USER [yes/no]: No                                        **
**                                                                            **
** MODIFICATION DATE : Feb 21, 2014     		                              **
**                                                                            **
*******************************************************************************/

/** \file
 *  \brief Main USB service task management.
 *
 *  This file contains the function definitions required for the main USB
 *  service task, which must be called from the user application to ensure that
 *  the USB connection to or from a connected USB device is maintained.
 *
 */

#ifndef __USBTASK_H__
#define __USBTASK_H__

	/* Includes: */
		#include "../Common/Common.h" /* IFX */
		#include "USBMode.h"
		#include "USBController.h"
		#include "Events.h"
		#include "StdRequestType.h"
		#include "StdDescriptors.h"

		#include "Endpoint.h" /* IFX */
		#include "Device.h" /* IFX */

/* Enable C linkage for C++ Compilers: */
		#if defined(__cplusplus)
			extern "C" {
		#endif
	void USB_Device_ProcessControlRequest(void); /*IFX*/



	/* Public Interface - May be used in end-application: */
		/* Global Variables: */
			/** Indicates if the USB interface is currently initialized but not
			 * necessarily connected to a host or device (i.e. if
			 * \ref USB_Init() has been run). If this is false, all other
			 * library globals related  to the USB driver are invalid.
			 *
			 *  \attention This variable should be treated as read-only in the
			 *  user application, and never manually
			 *             changed in value.
			 *
			 *  \ingroup Group_USBManagement
			 */
			extern volatile bool USB_IsInitialized;

			/** Structure containing the last received Control request when in
			 * Device mode (for use in user-applications inside of the
			 * \ref EVENT_USB_Device_ControlRequest() event, or for filling up
			 * with a control request to issue when in Host mode before calling
			 *  \ref USB_Host_SendControlRequest().
			 *
			 *  \note The contents of this structure is automatically endian-
			 *  corrected for the current CPU architecture.
			 *
			 *  \ingroup Group_USBManagement
			 */
			 extern volatile USB_Request_Header_t USB_ControlRequest;





			/** Indicates the current device state machine state. When in device
			 *  mode, this indicates the state via one of the values of the
			 *  \ref USB_Device_States_t enum values.
			 *
			 *  This value should not be altered by the user application as it
			 *  is handled automatically by the library. The only exception to
			 *  this rule is if the NO_LIMITED_CONTROLLER_CONNECT token is used
			 *  (see \ref EVENT_USB_Device_Connect() and
			 *  \ref EVENT_USB_Device_Disconnect() events).
			 *
			 *
			 *  \attention This variable should be treated as read-only in the
			 *  user application, and never manually changed in value except in
			 *  the circumstances outlined above.
			 *
			 *  \note This global is only present if the user application can be
			 *   a USB device. \n\n
			 *
			 *  \see \ref USB_Device_States_t for a list of possible device
			 *  states.
			 *
			 *  \ingroup Group_Device
			 */
			extern volatile uint8_t USB_DeviceState;



		/* Function Prototypes: */
			/** This is the main USB management task. The USB driver requires
			 * this task to be executed  continuously when the USB system is
			 * active (device attached in host mode, or attached to a host
			 *  in device mode) in order to manage USB communications. This task
			 *   may be executed inside an RTOS, fast timer ISR or the main user
			 *    application loop.
			 *
			 *  The USB task must be serviced within 30ms while in device mode,
			 *  or within 1ms while in host mode.The task may be serviced at all
			 *   times, or (for minimum CPU consumption):
			 *
			 *    - In device mode, it may be disabled at start-up, enabled on
			 *    the firing of the \ref EVENT_USB_Device_Connect() event and
			 *    disabled again on the firing of the
			 *    \ref EVENT_USB_Device_Disconnect() event.
			 *
			 *
			 *  \see \ref Group_Events for more information on the USB events.
			 *
			 *  \ingroup Group_USBManagement
			 */
			void USB_USBTask(void);


		/* Macros: */




	/* Disable C linkage for C++ Compilers: */
		#if defined(__cplusplus)
			}
		#endif

#endif

