package com.sudachen.uccm.compiler

import java.io.File

object Compiler extends Enumeration {
  val GCC, ARMCC = Value

  def fromString(name:String): Option[Value] = name match {
    case "armcc" => Some(ARMCC)
    case "gcc" => Some(GCC)
    case _ => None
  }

  def stringify(kind:Value):String = kind match {
    case GCC => "gcc"
    case ARMCC => "armcc"
  }

  def incPath(kind:Value):List[String] = kind match {
    case ARMCC => List("[armcc]\\include")
    case GCC => List("[gcc]\\arm-none-eabi\\include",
      "[gcc]\\lib\\gcc\\arm-none-eabi\\4.8.4\\include",
      "[gcc]\\lib\\gcc\\arm-none-eabi\\4.9.3\\include"
    )
  }

  def ccPath(kind:Value):String = kind match {
    case ARMCC => "[armcc]\\bin\\armcc.exe"
    case GCC => "[gcc]\\bin\\arm-none-eabi-gcc.exe"
  }

  def asmPath(kind:Value):Option[String] = kind match {
    case ARMCC => Some("[armcc]\\bin\\armasm.exe")
    case GCC => None
  }

  def odmpPath(kind:Value):Option[String] = kind match {
    case GCC => Some("[gcc]\\bin\\arm-none-eabi-objdump.exe")
    case ARMCC => None
  }

  def ldPath(kind:Value):String = kind match {
    case ARMCC => "[armcc]\\bin\\armlink.exe"
    case GCC => "[gcc]\\bin\\arm-none-eabi-gcc.exe"
  }

  def elfToHexCmdl(kind:Value,elfFile:File,outFile:File):String = kind match {
    case ARMCC => s"[armcc]\\bin\\fromelf.exe --i32 --output ${outFile.getCanonicalPath} ${elfFile.getCanonicalPath}"
    case GCC => s"[gcc]\\bin\\arm-none-eabi-objcopy -O ihex ${elfFile.getCanonicalPath} ${outFile.getCanonicalPath}"
  }

  def elfToBinCmdl(kind:Value,elfFile:File,outFile:File):String = kind match {
    case ARMCC => s"[armcc]\\bin\\fromelf.exe --bin --output ${outFile.getCanonicalPath} ${elfFile.getCanonicalPath}"
    case GCC => s"[gcc]\\bin\\arm-none-eabi-objcopy -O binary ${elfFile.getCanonicalPath} ${outFile.getCanonicalPath}"
  }
}
