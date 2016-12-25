
#pragma once

#include "../board.h"
#include "../leg.h"

#pragma uccm append(HAL_CONFIG) = "#define HAL_GPIO_MODULE_ENABLED\n"
#pragma uccm require(HAL_DRIVER(gpio))

__Inline
void gpio_setup_input(uccm_leg_t leg, uccm_gpio_input_t opt)
{
}

__Inline
void gpio_setup_output(uccm_leg_t leg, uccm_gpio_output_t opt)
{
}

__Inline
void gpio_setup_analog_input(uccm_leg_t leg)
{
}

__Forceinline
int gpio_get(uccm_leg_t leg)
{
}

__Forceinline
void gpio_set(uccm_leg_t leg, int val)
{
}

__Forceinline
int gpio_toggle(uccm_leg_t leg)
{
}