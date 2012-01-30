def ForLoop {
   for ( i <- 0 until args.length)
   {
     println( args(i))
   }
  
   args.foreach{ arg => println( arg)}
}
ForLoop