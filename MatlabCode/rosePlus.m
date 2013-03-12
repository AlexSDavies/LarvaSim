
function rosePlus(data,color,alpha)

    f = rose(data,32);
    
    set(f,'Color','k','LineWidth',2);
    set(gca,'View',[90,90]);
    patch(get(f,'XData'),get(f,'YData'),color,'FaceAlpha',alpha)