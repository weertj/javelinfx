package org.javelinfx.spatial;

import org.javelinfx.math.JL_Math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SP_Line {

  static public List<ISP_Position> lineToPolygon(List<ISP_Position>pPos, double pThickness, boolean pAddArrow, double pReduction ) {
    double rel = pThickness;
    ISP_Position firstpos = pPos.getFirst();
    double x1 = firstpos.x();
    double y1 = firstpos.y();
    List<ISP_Position> l1 = new ArrayList<>(8);
    List<ISP_Position> l2 = new ArrayList<>(8);
    double circleStart = rel;
    double circleReduction = circleStart/pPos.size();
    if (pReduction<=0) {
      circleReduction = 0;
    }
    for( ISP_Position pos : pPos ) {
      double endx = pos.x();
      double endy = pos.y();
      double[][] tangs = JL_Math.getTangents( x1, y1, circleStart, endx, endy, circleStart );
      circleStart -= circleReduction;
      if (tangs.length==0) {
        l1.add( firstpos.x(x1).y(y1) );
        l1.add( firstpos.x(x1).y(y1));
        l2.add( firstpos.x( endx).y( endy ));
        l2.add( firstpos.x( endx).y( endy ));
      } else {
        l1.add( firstpos.x( tangs[0][0]).y(tangs[0][1] ));
        l1.add( firstpos.x( tangs[0][2]).y( tangs[0][3] ));
        l2.add( firstpos.x( tangs[1][0]).y( tangs[1][1] ));
        l2.add( firstpos.x( tangs[1][2]).y( tangs[1][3] ));
      }
      x1 = endx;
      y1 = endy;
    }

    List<ISP_Position> path1 = new ArrayList<>();
    path1.add( l1.getFirst() );
    ISP_Position first = l1.removeFirst();
    for( ISP_Position pos : l1 ) {
      path1.add(pos);
    }
    Collections.reverse(l2);

    if (pAddArrow) {
      // **** Arrow
      ISP_Vector v1 = SP_Vector.of(l1.getLast(), l2.getFirst())
        .rotate(SP_Angle.of(Math.PI)).translate();
      path1.add(v1);
      v1 = v1.rotate(SP_Angle.of(-Math.PI * 0.75)).mul(2).translate();
      path1.add(v1);
      v1 = v1.rotate(SP_Angle.of(-Math.PI * 0.5)).translate();
      path1.add(v1);
    }

    for( ISP_Position pos : l2 ) {
      path1.add(pos );
    }
    path1.add( first);

    // **** Cleanup
    while( path1.getFirst().equals(path1.get(1)) ) {
      path1.removeFirst();
    }
    while( path1.getLast().equals(path1.get(path1.size()-2)) ) {
      path1.removeLast();
    }

    // **** Start
//    path1.removeLast();
//    IGEN_Vector v2 = GEN_Vector.create( path1.getLast(), path1.getFirst() )
//      .rotate( GEN_Angle.create(Math.PI*0.5)).translate();
//    path1.addFirst( v2 );
//    path1.addLast(v2);

    return path1;
  }

}
