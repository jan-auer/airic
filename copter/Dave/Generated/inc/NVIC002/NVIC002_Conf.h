/*******************************************************************************
**  DAVE App Name : NVIC002       App Version: 1.0.28               
**  This file is generated by DAVE, User modification to this file will be    **
**  overwritten at the next code generation.                                  **
*******************************************************************************/



/*CODE_BLOCK_BEGIN[NVIC002_Conf.h]*/
/**************************************************************************//**
 *
 * Copyright (C) 2014 Infineon Technologies AG. All rights reserved.
 *
 * Infineon Technologies AG (Infineon) is supplying this software for use with 
 * Infineon's microcontrollers.  
 * This file can be freely distributed within development tools that are 
 * supporting such microcontrollers. 
 *
 * THIS SOFTWARE IS PROVIDED "AS IS".  NO WARRANTIES, WHETHER EXPRESS, IMPLIED
 * OR STATUTORY, INCLUDING, BUT NOT LIMITED TO, IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE APPLY TO THIS SOFTWARE.
 * INFINEON SHALL NOT, IN ANY CIRCUMSTANCES, BE LIABLE FOR SPECIAL, INCIDENTAL, 
 * OR CONSEQUENTIAL DAMAGES, FOR ANY REASON WHATSOEVER.
 *
********************************************************************************
**                                                                            **
**                                                                            **
** PLATFORM : Infineon XMC4000/XMC1000 Series   			                  **
**                                                                            **
** COMPILER : Compiler Independent                                            **
**                                                                            **
** AUTHOR   : App Developer                                                   **
**                                                                            **
** MAY BE CHANGED BY USER [yes/no]: Yes                                       **
**                                                                            **
** MODIFICATION DATE : Jul 17, 2014                                           **
*******************************************************************************/

/*******************************************************************************
**                       Author(s) Identity                                   **
********************************************************************************
**                                                                            **
** Initials     Name                                                          **
** ---------------------------------------------------------------------------**
** SNR          App Developer                                                 **
*******************************************************************************/

/**
 * @file   NVIC002_Conf.h
 *
 * @App    Version NVIC002 <1.0.28>
 *
 * @brief  Configuration file generated based on UI settings 
 *         of NVIC002 App
 *
 */
/* Revision History	
 * 18  Feb 2013   v1.0.12 
 * 08  Oct 2013   v1.0.20 Updated as per coding guidelines
 * 17 Jul 2014   v1.0.28   End of line is corrected
 */
#ifndef _NVIC002_CONF_H_
#define _NVIC002_CONF_H_

#ifdef __cplusplus
extern "C" {
#endif

/****************************************************************************
@Defines
****************************************************************************/
#define DEBUG_APP false
  
/* User defined function mapped to IRQ_Hdlr_90 */
#define RC_RECEIVE_ISR    IRQ_Hdlr_90 

/* User defined function mapped to IRQ_Hdlr_5 */
#define MPU_EXT_INT_ISR    IRQ_Hdlr_5 

/* User defined function mapped to IRQ_Hdlr_91 */
#define ESC_UART_RX_ISR    IRQ_Hdlr_91 

/* User defined function mapped to IRQ_Hdlr_98 */
#define MPU9X50_I2C_FORMAT_ERROR_ISR    IRQ_Hdlr_98 

/* User defined function mapped to IRQ_Hdlr_89 */
#define BT_RECEIVE_ISR    IRQ_Hdlr_89 

/* User defined function mapped to IRQ_Hdlr_53 */
#define Utils_T_ISR    IRQ_Hdlr_53 

/* User defined function mapped to IRQ_Hdlr_52 */
#define AttControl_TIMER_ISR    IRQ_Hdlr_52 

/* User defined function mapped to IRQ_Hdlr_55 */
#define MagCalib_TIMER_ISR    IRQ_Hdlr_55 

/* User defined function mapped to IRQ_Hdlr_54 */
#define Monitoring_TIMER_ISR    IRQ_Hdlr_54 

/* User defined function mapped to IRQ_Hdlr_8 */
#define DPS_EXT_INT_ISR    IRQ_Hdlr_8 

/* User defined function mapped to IRQ_Hdlr_87 */
#define DPS310_I2C_FORMAT_ERROR_ISR    IRQ_Hdlr_87 


/**
 * @ingroup NVIC002_constants
 * @{
 */
#define NVIC002_NUM_INSTANCES 11U
/**
  * @}
  */
#ifdef __cplusplus
}
#endif

#endif  /* ifndef _NVIC002_CONF_H_ */
/*CODE_BLOCK_END*/
