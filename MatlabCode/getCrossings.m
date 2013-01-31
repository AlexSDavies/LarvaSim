function crossings = getCrossings(input,val)


    positiveCrossings = diff(input>val)==1;
    negativeCrossings = diff(input<-val)==1;
    
    crossings = positiveCrossings - negativeCrossings;
    crossings = crossings(crossings~=0);
    
end