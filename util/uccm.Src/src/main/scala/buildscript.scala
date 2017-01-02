package com.sudachen.uccm.buildscript

import com.sudachen.uccm.compiler.Compiler
import com.sudachen.uccm.debugger.Debugger

object BuildConfig extends Enumeration {
  val Debug, Release = Value

  def stringify(c:Value):String = c match {
    case Debug => "debug"
    case Release => "release"
  }

  def fromString(s:String):Option[Value] = s match {
    case "debug" => Some(Debug)
    case "release" => Some(Release)
    case _ => None
  }
}

case class BuildScript(ccTool:Compiler.Value,
                       dbgTool:Debugger.Value,
                       config:BuildConfig.Value,
                       cflags:List[String] = Nil,
                       sources:List[String] = Nil,
                       modules:List[String] = Nil,
                       libraries:List[String] = Nil,
                       generated:List[(String,String)] = Nil,
                       begin:List[String] = Nil,
                       end:List[String] = Nil,
                       ldflags:List[String] = Nil,
                       asflags:List[String] = Nil) {
  def toXML : scala.xml.Node = {
    <uccm>
      <cctool>
        {Compiler.stringify(ccTool)}
      </cctool>
      <dbgtool>
        {Debugger.stringify(dbgTool)}
      </dbgtool>
      <config>
        {BuildConfig.stringify(config)}
      </config>
      <cflags>
        {cflags map { i =>
        <flag>
          {i}
        </flag>} }
      </cflags>
      <ldflags>
        {ldflags map { i =>
        <flag>
          {i}
        </flag>} }
      </ldflags>
      <asflags>
        {asflags map { i =>
        <flag>
          {i}
        </flag>} }
      </asflags>
      <sources>
        {sources map { i =>
        <file>
          {i}
        </file>} }
      </sources>
      <modules>
        {modules map { i =>
        <file>
          {i}
        </file>} }
      </modules>
      <libraries>
        {libraries map { i =>
        <file>
          {i}
        </file>} }
      </libraries>
      <generated>
        {generated map { i =>
        <append>
          <file>
            {i._1}
          </file>
          <content>
            {scala.xml.PCData(i._2)}
          </content>
        </append>} }
      </generated>
    </uccm>
  }
}

object BuildScript {
  def fromXML(xml: scala.xml.Node) : BuildScript = {
    def bs(c:Char):Boolean = c match { case ' '|'\n'|'\r' => true case _ => false }
    def ns(s:String) = s.dropWhile{bs}.reverse.dropWhile{bs}.reverse
    BuildScript(
      Compiler.fromString(ns((xml\"cctool" ).text)).get,
      Debugger.fromString(ns((xml\"dbgtool" ).text)).get,
      BuildConfig.fromString(ns((xml\"config" ).text)).get,
      cflags = (xml\"cflags"\"flag").map{ x => ns(x.text)}.toList,
      ldflags = (xml\"ldflags"\"flag").map{ x => ns(x.text)}.toList,
      asflags = (xml\"asflags"\"flag").map{ x => ns(x.text)}.toList,
      sources = (xml\"sources"\"file").map{ x => ns(x.text)}.toList,
      modules = (xml\"modules"\"file").map{ x => ns(x.text)}.toList,
      libraries = (xml\"libraries"\"file").map{ x => ns(x.text)}.toList,
      generated = (xml\"generated"\"append").map{ x => (ns((x\"name").text),ns((x\"content").text))}.toList
    )}
}

