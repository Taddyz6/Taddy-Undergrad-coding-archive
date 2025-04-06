import './App.css'
import { useEffect, useState } from 'react'
import Trend from './components/Trend';
import TopRanking from './components/TopRanking';
import Distribution from './components/Distribution';
import Debug from './components/Debug';

function App() {
  const [isPageState, setIsPageState] = useState('');

  function handleClickHome(){
    setIsPageState('');
  }
  function handleClickTrend(){
    setIsPageState('Trend');
  }
  function handleClickTopRanking(){
    setIsPageState('Top Ranking');
  }
  function handleClickDistribution(){
    setIsPageState('Distribution');
  }
  function handleClickDebugPage(){
    setIsPageState('Debug');
  }

  return (
    <div>
      <div className='banner'>
        <h2 onClick={handleClickHome}> Mental Health Assessment</h2>
      </div>
      <div className='body-container'>
        <div className='sidebar-container'>
          <div className='sidebar-item' onClick={handleClickTrend}>Trend</div>
          <div className='sidebar-item' onClick={handleClickTopRanking}>By Age</div>
          <div className='sidebar-item' onClick={handleClickDistribution}>Distribution</div>
          <></>
        </div>
        <div className='card'>
          {(() => {
          switch (isPageState) {
            case 'Trend':
              return <Trend/>;
            case 'Top Ranking':
              return <TopRanking/>;
            case 'Distribution':
              return <Distribution/>;
            default:
              return <div> 
                  <p>
                  This website utilizes datasets from CDC's House Pulse 
                  and associate data in a meaningful format.
                  The data mainly involves statistics of confidence intervals of demographic groups, the indicators, date ranges
                </p>
                <p> 
                  Please select one of the topics in the sidebar.
                </p>
              </div>;
          }
          })()}
        </div>
      </div>
    </div>
  )
}

export default App
