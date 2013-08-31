package com.ajjpj.amapper.core

/**
 * @author arno
 */
case class Path(segments: List[PathSegment]) {
  def parent = Path(segments.reverse.tail.reverse)
  def last = segments.reverse.head

  override def toString = "Path{" + segments.mkString(".") + "}"
}

class PathBuilder(reversePath: List[PathSegment]) {
  def this() = this (Nil)
  def +(segment: PathSegment) = new PathBuilder(segment :: reversePath)
  def build = Path (reversePath.reverse)
}

sealed trait PathSegment {
  def name: String
}
case class SimplePathSegment(name: String) extends PathSegment {
  override def toString = name
}
/**
 * combination of name and key must be unique per location in an object graph. For a Set,
 *  the key should be a tuple of attributes that the equals method is based on.
 */
//TODO is that necessary? not implemented that way as yet
case class ParameterizedPathSegment(name: String, key: AnyRef) extends PathSegment {
  override def toString = name + "(" + key + ")"
}
case class IndexedPathSegment(name: String, index: Int) extends PathSegment {
  override def toString = name + "[" + index + "]"
}

