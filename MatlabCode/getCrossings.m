function crossings = getCrossings(input,val)

    % Get points at which input goes from less than val to greater than val
    positiveCrossings = diff(input>val)==1;
    negativeCrossings = diff(input<-val)==1;
    
    % Combine positive and negative crossings
    crossings = positiveCrossings + negativeCrossings;
    
end